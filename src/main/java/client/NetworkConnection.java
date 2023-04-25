package client;

import common.networkStructures.Request;
import common.networkStructures.Response;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.Iterator;

public class NetworkConnection {
    private final int BUFFER_SIZE = 1024 * 1024;
    private InetAddress host;
    private Response returnResponse;
    private boolean regFlag = false;
    private boolean authFlag = false;
    private String authResponse;

    int port;
    SocketAddress socketAddress;
    SocketChannel socketChannel;
    Selector selector;

    public NetworkConnection(String address, int port) throws IOException {
        host = InetAddress.getByName(address);
        this.socketAddress = new InetSocketAddress(host, port);
        selector = Selector.open();
    }

    public boolean isRegFlag() {
        return regFlag;
    }

    public boolean isAuthFlag() {
        return authFlag;
    }

    public String getAuthResponse() {
        return authResponse;
    }

    public void connectionManage(Request request) throws Exception {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.socket().setSoLinger(true, 0);
            socketChannel.connect(socketAddress);

            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            loop:
            while (true) {
                Thread.sleep(50);
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                if (!keyIterator.hasNext()) {
                    selector = Selector.open();
                    socketChannel.close();
                    break;
                }
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isConnectable()) {
                        if (socketChannel.finishConnect()) {
                            socketChannel.register(selector, SelectionKey.OP_WRITE);
                        }
                    }
                    if (key.isWritable()) {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                        objectOutputStream.writeObject(request);


                        ByteBuffer buf = ByteBuffer.wrap(out.toByteArray());
                        buf.rewind();

                        while (buf.hasRemaining()) {
                            socketChannel.write(buf);
                        }
                        buf.clear();
                        out.close();
                        objectOutputStream.close();
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {

                        byte[] data = new byte[BUFFER_SIZE];
                        ByteBuffer buf = ByteBuffer.wrap(data);
                        buf.clear();
                        socketChannel.read(buf);


                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

                        try {

                            Response response = (Response) objectInputStream.readObject();
                            if (response.getOutput().split(" ")[0].equals("!<>!")) {
                                int condition = Integer.parseInt(response.getOutput().split(" ")[1]);
                                if (condition == 0) {
                                    System.out.println("Регистрация прошла успешно");
                                    regFlag = true;
                                    authFlag = true;
                                }
                                if (condition == 1) {
                                    System.out.println("Такой пользователь уже есть");
                                }
                                if (condition == 2) {
                                    System.out.println("Авторизация прошла успешно");
                                    regFlag = true;
                                    authFlag = true;
                                }
                                if (condition == 3) {
                                    System.out.println("Неверный пароль");
                                }
                                if (condition == 4) {
                                    System.out.println("Нет такого пользователя");
                                }
                            } else {
                                System.out.println(response.getOutput());
                            }


                        } catch (java.io.EOFException e) {
                            System.out.println("Слишком большие данные для маленького клиента");
                        }
                        buf.clear();
                        byteArrayInputStream.close();
                        objectInputStream.close();
                        socketChannel.close();


                        break loop;

                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException | NotYetConnectedException ignored){
            if (!socketChannel.isConnected()){
                System.out.println("Сервер разорвал подключение");
                System.exit(0);
            }

        }
    }

}
