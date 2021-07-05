package cn.cube.base.demo;

import cn.cube.base.core.util.JsonUtil;
import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.core.util.OkHttpUtil;
import cn.cube.base.core.util.StringUtils;
import cn.cube.base.demo.crawling.Request;
import cn.cube.base.demo.crawling.Response;
import cn.cube.base.demo.crawling.Uri;
import cn.cube.base.demo.crawling.request.TagQueryRequest;
import cn.cube.base.demo.crawling.response.TagQueryResponse;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:2020/2/18
 * Email:406504302@qq.com
 */
public class InstagramTest extends BaseTest {
    private static final Logger logger = LoggerUtils.getLogger(InstagramTest.class);

    @Autowired
    private OkHttpUtil httpUtil;
    String tagUrl = "https://www.instagram.com/explore/tags/{}/";

    public void query() {
        Map<String, String> headers = Maps.newHashMap();
        headers.put("authority", "www.instagram.com");
        headers.put("accept-encoding", "gzip, deflate, br");
        headers.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,la;q=0.7");
        headers.put("cache-control", "no-cache");
        headers.put("referer", "https://www.instagram.com/");
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
        headers.put("x-ig-app-id", "936619743392459");
        headers.put("x-instagram-gis", "8f382d24b07524ad90b4f5ed5d6fccdb");
        headers.put("x-requested-with", "XMLHttpRequest");
        headers.put("scheme", "https");
        headers.put("accept", "*/*");
        headers.put("cookie", "mid=XjZqNgALAAGLwGsZSyXYevJoxSdv; ig_cb=1; ig_did=A7028805-16AE-442A-9FB6-8C9C94A59226; fbm_124024574287414=base_domain=.instagram.com; sessionid=26206024172%3AJbEuHsS5Rez26z%3A14; csrftoken=YZaMlJaaanaH1o9PdFVEyU03NPGN7xVd; ds_user_id=26206024172; rur=PRN; urlgen=\"{\\\"89.187.161.206\\\": 60068}:1j4K1a:DSS0D3sX-AH3TpXIbFrFSkGOz6Y\"");
        //String url = "https://www.instagram.com/graphql/query/?query_hash=477b65a610463740ccdb83135b2014db&shortcode=B1Iu3-VlXJt";
        TagQueryRequest data = new TagQueryRequest();
        data.setTag_name("美女");
        boolean isHas_next_page = false;
        Request request = new Request();
        data.setFirst(8);
        int page =1;
        do {

            request.setVariables(data);
            Response<TagQueryResponse> response = new Response<>();
            Map map = request.toMap();
            String s = httpUtil.doGet(Uri.URI,map , headers);if (StringUtils.isEmpty(s)) {
                break;
            }
            response = JsonUtil.toObj(s, response.getClass());

            TagQueryResponse responseData = JsonUtil.toObj(JsonUtil.toJson(response.getData()), TagQueryResponse.class);
            TagQueryResponse.Hashtag hashtag = responseData.getHashtag();
            TagQueryResponse.Page_info page_info = hashtag.getEdge_hashtag_to_media().getPage_info();
            String end_cursor = page_info.getEnd_cursor();
            data.setAfter(end_cursor);
            isHas_next_page = page_info.isHas_next_page();
            if (isHas_next_page){
                logger.info("-------------{}------------------",page);
            }

            List<TagQueryResponse.Edge_hashtag_to_media_Edge> edges = hashtag.getEdge_hashtag_to_media().getEdges();
            edges.forEach(edge_hashtag_to_media_edge -> {
                logger.info(JsonUtil.toJson(edge_hashtag_to_media_edge));
            });
            page++;
        } while (isHas_next_page);

    }
}
