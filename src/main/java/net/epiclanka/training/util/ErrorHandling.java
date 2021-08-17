package net.epiclanka.training.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("error-codes")
public class ErrorHandling {
    private String saveErrorCode;
    private String saveErrorMessage;
    private String invalidData;
    private String msgConErrorCode;
    private String msgConErrorMsg;
    private String deleteErrorCode;
    private String deleteErrorMessage;



    public String getSaveErrorCode() {
        return saveErrorCode;
    }

    public void setSaveErrorCode(String saveErrorCode) {
        this.saveErrorCode = saveErrorCode;
    }

    public String getSaveErrorMessage() {
        return saveErrorMessage;
    }

    public void setSaveErrorMessage(String saveErrorMessage) {
        this.saveErrorMessage = saveErrorMessage;
    }

    public String getInvalidData() {
        return invalidData;
    }

    public void setInvalidData(String invalidData) {
        this.invalidData = invalidData;
    }

    public String getMsgConErrorCode() {
        return msgConErrorCode;
    }

    public void setMsgConErrorCode(String msgConErrorCode) {
        this.msgConErrorCode = msgConErrorCode;
    }

    public String getMsgConErrorMsg() {
        return msgConErrorMsg;
    }

    public void setMsgConErrorMsg(String msgConErrorMsg) {
        this.msgConErrorMsg = msgConErrorMsg;
    }

    public String getDeleteErrorCode() {
        return deleteErrorCode;
    }

    public void setDeleteErrorCode(String deleteErrorCode) {
        this.deleteErrorCode = deleteErrorCode;
    }

    public String getDeleteErrorMessage() {
        return deleteErrorMessage;
    }

    public void setDeleteErrorMessage(String deleteErrorMessage) {
        this.deleteErrorMessage = deleteErrorMessage;
    }
}
