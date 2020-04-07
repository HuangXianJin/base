package com.huangxj.common.core.swagger;

/**
 * @ClassName SwaggerConfig
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-08-05 14:38
 * @Version V1.0
 **/
//@Configuration
//@EnableSwagger2
//@Profile({"dev", "test"})
public class SwaggerConfig {
//
//    @Bean
//    public Docket createRestApi() {
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<Parameter>();
//        tokenPar.name("authorization").defaultValue("").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar.build());
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .apis(RequestHandlerSelectors.basePackage("com.huangxj"))
//                .paths(PathSelectors.any())
//                .build().globalOperationParameters(pars)
//                .apiInfo(apiInfo()).groupName("0-api");
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Swagger API")
//                .description("")
//                .termsOfServiceUrl("")
//                .version("2.0")
//                .build();
//    }
}
