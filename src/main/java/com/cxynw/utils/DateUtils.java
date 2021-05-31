package com.cxynw.utils;

import lombok.NonNull;

import java.util.Date;

/**
 * Date utilities.
 *
 * @date 2/3/21
 */
public class DateUtils {

    private DateUtils(){}


    /**
     * Gets current date.
     *
     * @return current date
     */
    @NonNull
    public static Date now(){ return new Date(); }

}
