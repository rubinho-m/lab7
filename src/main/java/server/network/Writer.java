package server.network;

import common.networkStructures.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Writer {
    private OutputStream outputStream;
    private static final Logger logger = LogManager.getLogger(Writer.class);

    public Writer(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        byte[] newArray = byteArrayOutputStream.toByteArray();
        outputStream.write(newArray);
        outputStream.flush();
        logger.info("RESPONSE HAS BEEN SENT");
    }
}
