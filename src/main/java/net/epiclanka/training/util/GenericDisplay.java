package net.epiclanka.training.util;


import org.apache.log4j.Logger;

public class GenericDisplay {
    private static Logger log = Logger.getLogger(GenericDisplay.class);
    private GenericDisplay() {
    }

    public static <T> void display(T element) {
        log.info("Customer Info\n" + element);
    }
}
