package com.csatimes.dojma.utilities;

import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * Values specific to DoJMA api.
 * Version of DoJMA api used for reference was 1.1.1.
 * See http://bitsherald.org/api/info/ for current version.
 *
 * @author Rushikesh Jogdand.
 */
public final class DojmaApiValues {
    public static final String DOJMA_API_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DOJMA_API_BASE_URL = "http://bitsherald.org/api/";

    public static final SimpleDateFormat DOJMA_API_DATE_SDF = new SimpleDateFormat(DOJMA_API_DATE_FORMAT, Locale.ENGLISH);
}
