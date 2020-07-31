package cn.wolfcode.exception;

/**
 * 自定义的逻辑异常
 */
public class LogicException extends RuntimeException{

    public LogicException(String errorMsg){
        //调用父类的构造器,把错误信息传给父类
        super(errorMsg);
    }
}
