package util;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class Javasocket extends Socket {
    public Socket socket = null;
    Logger        logger;

    public Javasocket() {
        // TODO Auto-generated constructor stub
        logger = Logger.getLogger("Javasocket");
    }

    private Socket startConnection() {
        Pause pause = new Pause();
        String info;
        for (int i = 0; i < 10; i++) {
            try {
                socket = new Socket("localhost", 7788);
                System.out.println("start java socket 7788");
                pause.seconds(1);
                break;
            } catch (UnknownHostException e) {
                logger.info("UnknownHostException: " + e.getMessage());
                pause.seconds(5);
            } catch (IOException e) {
                logger.info("IOException: " + e.getMessage());
                pause.seconds(5);
            }
        }

        return socket;
    }

    private Socket startConnectionToWinClient(String IP, int Port) {
        Pause pause = new Pause();
        String info;
        boolean isFailed = true;
        for (int i = 0; i < 10; i++) {
            try {
                socket = new Socket(IP, Port);
                logger.info(String.format("start java socket to %s:%s", IP, Port));
                pause.seconds(1);
                isFailed = false;
                break;
            } catch (UnknownHostException e) {
                logger.info("UnknownHostException: " + e.getMessage());
                pause.seconds(5);
            } catch (IOException e) {
                logger.info("IOException: " + e.getMessage());
                pause.seconds(5);
            }
        }

        assertTrue(!isFailed, "We must able to connect to windows client");
        return socket;
    }

    private void stopConnection() {
        // 2、获取输出流，向服务器端发送信息
        OutputStream os = null;
        try {
            os = socket.getOutputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 字节输出流
        PrintWriter pw = new PrintWriter(os);// 将输出流包装成打印流
        pw.write("quit");
        pw.flush();
        try {
            socket.shutdownOutput();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void wakeUpTclShell() {
        startConnection();
        stopConnection();
    }

    public void waitForTclShell() {
        startConnection();
        stopConnection();
    }

    public String sendCommandToWinClient(String IP, String Port, String Command) {
        logger.info(String.format(": <%s>-<%s>-<%s>", IP, Port, Command));
        startConnectionToWinClient(IP, Integer.valueOf(Port));
        String output = getCommandOutputForWinClient(Command);
        Pause pause = new Pause();
        pause.seconds(2);
        try {
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        logger.info(output);
        return output;
    }

    public String getCommandOutputForWinClient(String command) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        pw.write(command);
        pw.flush();
        BufferedReader is;
        StringBuffer sb = null;
        try {
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sb = new StringBuffer();
            String temp;
            int index;
            while ((temp = is.readLine()) != null) {
                sb.append(temp);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void sendParamToTclShell(String param1, String param2) {
        startConnection();
        sendCommandToTclShell("TG_param_set " + param1 + " " + param2);
        Pause pause = new Pause();
        pause.seconds(2);
        try {
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String sendCommandToTclShell(String tclCommand) {
        String info = "";
        OutputStream os = null;
        try {
            os = socket.getOutputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 字节输出流
        PrintWriter pw = new PrintWriter(os);// 将输出流包装成打印流
        pw.write(tclCommand);
        pw.flush();
        try {
            socket.shutdownOutput();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 3、获取输入流，并读取服务器端的响应信息
        InputStream is = null;
        try {
            is = socket.getInputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Pause pause = new Pause();
        try {
            info = br.readLine();
            System.out.println("get message from tcl:" + info);
            pause.seconds(1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 读取TCL脚本的运行结果
     *
     * @return
     * @throws UnknownHostException
     * @throws IOException
     */
    public boolean readResultFromTclShell() {
        startConnection();
        Pause pause = new Pause();
        pause.seconds(3);
        String info = sendCommandToTclShell("TG_GetResult");
        try {
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 关闭Socket
        boolean result = false;
        if (info.indexOf(">+OK") > 1) {
            result = true;
        }
        return result;
    }
}
