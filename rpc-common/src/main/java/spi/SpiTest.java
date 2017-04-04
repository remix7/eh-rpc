package spi;

import java.util.Iterator;

public class SpiTest {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException,
                                          ClassNotFoundException {

        Iterator it = sun.misc.Service.providers(SPIService.class);
        while (it.hasNext()) {
            SPIService service = (SPIService) it.next();
            service.test();
        }
    }
}
