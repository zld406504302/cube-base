package cn.cube.base.third.qiniu;

import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.core.util.StringUtils;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

/**
 * Description:QiniuUtil
 * Author:zhanglida
 * Date:2020/8/19
 * Email:406504302@qq.com
 */
@Component
public class QiniuUtil {
    private static final Logger logger = LoggerUtils.getLogger(QiniuUtil.class);
    @Autowired
    private QiniuConfig qiniuConfig;

    public String generateToken() {
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        return auth.uploadToken(qiniuConfig.getBucket());
    }

    public String upload(File file, String key) {
        if (null == file) {
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID, "文件不能为空");
        }
        if (StringUtils.isEmpty(key)) {
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID, "key不能为空");
        }
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huabei());
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        // 设置分片上传并发，1：采用同步上传；大于1：采用并发上传
        cfg.resumableUploadMaxConcurrentTaskCount = 2;
        //生成上传凭证，然后准备上传
        String bucket = qiniuConfig.getBucket();
        String localFilePath = file.getAbsolutePath();
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String time = String.valueOf(System.currentTimeMillis());
        key = key + "/" + time + "-" + file.getName();
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(bucket);
        String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), bucket).toString();
        //设置断点续传文件进度保存目录
        try {
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);

            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (Exception ex) {
            logger.error("qiniu upload fail", ex);
        }
        return "";
    }
}

