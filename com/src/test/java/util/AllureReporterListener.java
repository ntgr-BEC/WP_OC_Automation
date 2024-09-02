package util;

import java.io.IOException;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

import io.qameta.allure.Attachment;
import webportal.param.WebportalParam;

public class AllureReporterListener implements IHookable {
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        callBack.runTestMethod(testResult);
        if ((testResult.getThrowable() != null) && !WebportalParam.enableDebug) {
            try {
                takeScreenShot(testResult.getMethod().getMethodName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Attachment(value = "Page Screenshot", type = "image/png")
    private byte[] takeScreenShot(String methodName) throws IOException {
        return WebportalParam.takeScreenShot();
    }
}
