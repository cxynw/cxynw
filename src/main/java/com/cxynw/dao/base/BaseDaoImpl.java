package com.cxynw.dao.base;

import com.cxynw.repository.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class BaseDaoImpl<T, ID> implements BaseDao<T,ID> {

    protected final BaseRepository<T,ID> baseRepository;

    public BaseDaoImpl(@NotNull BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public Optional<T> findById(ID id) {
        Optional<T> optional = baseRepository.findById(id);
        return optional;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        Page<T> page = baseRepository.findAll(pageable);
        return page;
    }

    @Override
    public List<T> findByProperties(T t) {
        List<Class> classes = new ArrayList<>(16);
        Class<?> tClass;
        Class<?> superclass = t.getClass();
        do {
            tClass = superclass;
            classes.add(tClass);
            superclass  = tClass.getSuperclass();
        }while (superclass != null);

        List<Field> needFields = new ArrayList<>(24);
        classes.forEach((item)->{
            Field[] declaredFields = item.getDeclaredFields();
            Field[] fields = item.getFields();

            getEntityColumns(t, needFields, declaredFields);
            getEntityColumns(t, needFields, fields);
        });


        List<T> result = baseRepository.findAll((root, query, criteriaBuilder) -> {

            Stream<Predicate> predicateStream = needFields.stream().map((item) -> {
                Path<Object> objectPath = root.get(item.getName());
                item.setAccessible(true);
                Object o = null;
                try {
                    o = item.get(t);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return criteriaBuilder.equal(objectPath, o);
            });

            query.where(predicateStream.toArray(Predicate[]::new));
            return query.getRestriction();
        });

        return result;
    }

    private void getEntityColumns(T t, List<Field> needFields, Field[] declaredFields) {
        Arrays.stream(declaredFields).forEach((declaredField)->{
            declaredField.setAccessible(true);
            try {
                if((declaredField.getAnnotation(Column.class) != null ||
                        declaredField.getAnnotation(Id.class) != null) && declaredField.get(t) != null){
                    needFields.add(declaredField);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean deleteById(ID id) {
        boolean existsById = baseRepository.existsById(id);
        if(existsById){
            baseRepository.deleteById(id);
        }
        return existsById;
    }

    @Override
    public Optional<T> insert(T t) {
        ID id = getId(t);
        if(id != null &&
                baseRepository.existsById(id)){
            throw new IllegalArgumentException("id 已经被使用");
        }
        return Optional.of(baseRepository.save(t));
    }

    @Override
    public Optional<T> update(T t) {
        ID id = getId(t);
        if(id == null ||
                baseRepository.existsById(id) == false){
            Optional.empty();
        }
        return Optional.of(baseRepository.save(t));
    }

    abstract protected ID getId(T t);

}
