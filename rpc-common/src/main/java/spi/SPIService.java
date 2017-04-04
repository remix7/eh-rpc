package spi;

/**
 * SPI的全名为Service Provider Interface.普通开发人员可能不熟悉，因为这个是针对厂商或者插件的。
 * 在java.util.ServiceLoader的文档里有比较详细的介绍。究其思想，其实是和"Callback"差不多。
 * “Callback”的思想是在我们调用API的时候，我们可以自己写一段逻辑代码，传入到API里面，
 * API内部在合适的时候会调用它，从而实现某种程度的“定制”。
 */
public interface SPIService {

    void test();
}
