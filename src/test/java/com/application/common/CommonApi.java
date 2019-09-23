package com.application.common;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.openqa.selenium.Cookie;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.*;

/**
 * @author Maciej Kocol
 * <p>
 * This is the API class that is used to reset changes.
 */
public class CommonApi {

    /**
     * Stores cookies to file after login.
     *
     * @param cookies
     */
    public static void storeCookies(List<Cookie> cookies) {
        File file = new File(System.getenv("cookie_data"));
        try {
            // Delete old file if exists
            file.delete();
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter buffWrite = new BufferedWriter(fileWrite);

            // loop for getting the cookie information
            for (Cookie ck : cookies) {
                buffWrite.write((ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";" + ck.getExpiry() + ";" + ck.isSecure()));
                buffWrite.newLine();
            }
            buffWrite.close();
            fileWrite.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retreives stored cookie from file by name
     *
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(String cookieName) {
        try {
            File file = new File(System.getenv("cookie_data"));
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader BuffReader = new BufferedReader(fileReader);
            String line;
            while ((line = BuffReader.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(line, ";");
                while (token.hasMoreTokens()) {
                    String name = token.nextToken();
                    String value = token.nextToken();
                    String domain = token.nextToken();
                    String path = token.nextToken();
                    Date expiry = null;
                    String val;
                    if (!(val = token.nextToken()).equals("null")) {
                        String EXPIRY_FORMAT = "EEE MMM d HH:mm:ss z yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(EXPIRY_FORMAT);
                        expiry = sdf.parse(val);
                    }
                    Boolean isSecure = Boolean.valueOf(token.nextToken());
                    if (name.equalsIgnoreCase(cookieName)) {
                        return new Cookie(name, value, domain, path, expiry, isSecure);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Validates employee data using fields obtained from API get request
     *
     * @param apiURL
     * @param entry
     * @param value
     */
    public static void validateEmployeeData(String apiURL, String entry, String value) {
        String cookie = "owSessionKey=" + getCookie("owSessionKey").getValue() + ";";
        given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .header("cache-control", "no-cache")
                .header("cookie", cookie)
                .when().get(apiURL)
                .then().assertThat()
                .statusCode(200)
                .body(entry, equalTo(value));
        //.extract().response();
    }

    /**
     * Sets employee data with API patch request
     *
     * @param apiURL
     * @param entry
     * @param value
     */
    public static void setEmployeeData(String apiURL, String entry, String value) {
        String csrftoken = getCookie("csrftoken").getValue();
        String cookie = "";
        cookie += "csrftoken=" + csrftoken + ";";
        cookie += "owSessionKey=" + getCookie("owSessionKey").getValue() + ";";
        try {
            given().config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
                    .formParam(entry, value)
                    .header("cache-control", "no-cache")
                    .header("referer", apiURL)
                    .header("x-csrftoken", csrftoken)
                    .header("cookie", cookie)
                    .when()
                    .patch(apiURL)
                    .then().statusCode(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
