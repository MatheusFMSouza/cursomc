package br.com.matheus.cursomc.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.athena.AmazonAthenaClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String buckName;

    public void uploadFile(String localFilePath) {
        try {
            File file = new File(localFilePath);
            LOG.info("Iniciando upload");
            s3client.putObject(new PutObjectRequest(buckName, "teste.png", file));
            LOG.info("Upload finalizado");
        } catch (AmazonServiceException ams) {
            LOG.info("AmazonServiceException: " + ams.getErrorMessage());
            LOG.info("Status code: " + ams.getErrorCode());
        }catch (AmazonClientException am){
            LOG.info("AmazonClientException: " + am.getMessage());
        }
    }

}
