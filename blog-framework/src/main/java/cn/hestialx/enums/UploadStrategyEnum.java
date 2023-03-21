package cn.hestialx.enums;

/**
 * @author lixu
 * @create 2023-02-28-15:39
 */
public enum UploadStrategyEnum {
    TX_OSS("txcos","TXOSSUploadStrategy"),
    LOCAL_OSS("local","LocalUploadSrategy");


    private String model;
    private String strategyName;

    UploadStrategyEnum(String model,String strategyName){
        this.model = model;
        this.strategyName = strategyName;
    }
    public static String getStrategy(String model){
        UploadStrategyEnum[] values = UploadStrategyEnum.values();
        for (UploadStrategyEnum value : values) {
            if(value.model.equals((model))){
                return value.strategyName;
            }
        }
        return null;
    }
}
