package com.cxynw.service.base;

import com.cxynw.exception.NotFoundException;
import com.cxynw.repository.base.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@Deprecated
@Slf4j
public abstract class AbstractCrudService<DOMAIN,ID> implements CrudService<DOMAIN,ID>{

    private final BaseRepository<DOMAIN, ID> repository;
    private final String domainName;

    @SuppressWarnings("unchecked")
    protected AbstractCrudService(BaseRepository<DOMAIN, ID> repository){
        this.repository = repository;

        // Get domain name
        Class<DOMAIN> domainClass = (Class<DOMAIN>) fetchType();
        domainName = domainClass.getSimpleName();
    }

    /**
     * Gets actual generic type.
     *
     * @return real generic type will be returned
     */
    private Type fetchType() {
        return ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public Page<DOMAIN> listAll(Pageable pageable) {
        Assert.notNull(pageable, "Pageable info must not be null");

        return repository.findAll(pageable);
    }

    @Override
    public Optional<DOMAIN> findById(ID id) {
        Assert.notNull(id, domainName + " id must not be null");

        return repository.findById(id);
    }

    @Override
    public DOMAIN getById(ID id) throws NotFoundException {
        return findById(id).orElseThrow(
                () -> new NotFoundException(domainName + " was not found or has been deleted"));
    }

    @Override
    public boolean existsById(ID id) {
        Assert.notNull(id, domainName + " id must not be null");

        return repository.existsById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public DOMAIN create(DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");

        return repository.save(domain);
    }

    @Override
    public List<DOMAIN> createInBatch(Collection<DOMAIN> domains) {
        Assert.notNull(domains, domainName + " data must not be null");

        return CollectionUtils.isEmpty(domains) ? Collections.emptyList() :
                repository.saveAll(domains);
    }

    @Override
    public DOMAIN update(DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");

        return repository.saveAndFlush(domain);
    }

    @Override
    public List<DOMAIN> updateInBatch(Collection<DOMAIN> domains) {
        Assert.notNull(domains, domainName + " data must not be null");

        return CollectionUtils.isEmpty(domains) ? Collections.emptyList() :
                repository.saveAll(domains);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public DOMAIN removeById(ID id) {
        Assert.notNull(id, domainName + " data must not be null");

        // Get non null domain by id
        DOMAIN domain = getById(id);

        // Remove it
        remove(domain);

        // return the deleted domain
        return domain;
    }

    @Override
    public DOMAIN removeByIdOfNullable(ID id) {
        Assert.notNull(id, domainName + " data must not be null");

        return findById(id).map(domain -> {
            remove(domain);
            return domain;
        }).orElse(null);
    }

    @Override
    public void remove(DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");

        repository.delete(domain);
    }

    @Override
    public void removeInBatch(Collection<ID> ids) {
        Assert.notNull(ids, domainName + " data must not be null");

        if (CollectionUtils.isEmpty(ids)) {
            log.debug(domainName + " id collection is empty");
            return;
        }

        ArrayList domains = new ArrayList<DOMAIN>(ids.size());
        ids.forEach((item)->{
            DOMAIN domain = createDomain(item);
            domains.add(domain);
        });

        repository.deleteInBatch(domains);
    }

    @SuppressWarnings("unchecked")
    protected DOMAIN createDomain(ID id){
        Class<DOMAIN> domainClass = (Class<DOMAIN>) fetchType();
        try {
            DOMAIN domain = (DOMAIN) domainClass.getConstructor().newInstance();
            Method method = domainClass.getMethod("setId", id.getClass());
            method.invoke(domain, id);
            return domain;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("create domain fail");
    }

}
