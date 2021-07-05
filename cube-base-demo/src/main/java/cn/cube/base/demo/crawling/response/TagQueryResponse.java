package cn.cube.base.demo.crawling.response;

import java.util.List;

/**
 * Description:TagQueryResponse
 * Author:zhanglida
 * Date:2020/2/21
 * Email:406504302@qq.com
 */
public class TagQueryResponse {
    private Hashtag hashtag;

    public Hashtag getHashtag() {
        return hashtag;
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
    }

    public static class Hashtag{
        //tag id
        private String id;
        //tag name
        private String name;
        //tag img
        private String profile_pic_url;

        private Edge_hashtag_to_media edge_hashtag_to_media;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfile_pic_url() {
            return profile_pic_url;
        }

        public void setProfile_pic_url(String profile_pic_url) {
            this.profile_pic_url = profile_pic_url;
        }

        public Edge_hashtag_to_media getEdge_hashtag_to_media() {
            return edge_hashtag_to_media;
        }

        public void setEdge_hashtag_to_media(Edge_hashtag_to_media edge_hashtag_to_media) {
            this.edge_hashtag_to_media = edge_hashtag_to_media;
        }
    }

    public static class Edge_hashtag_to_media{
        private Integer count;
        private Page_info page_info;
        private List<Edge_hashtag_to_media_Edge>  edges;

        public Integer getCount() {
            return count;
        }
        public void setCount(Integer count) {
            this.count = count;
        }

        public Page_info getPage_info() {
            return page_info;
        }

        public void setPage_info(Page_info page_info) {
            this.page_info = page_info;
        }

        public List<Edge_hashtag_to_media_Edge> getEdges() {
            return edges;
        }

        public void setEdges(List<Edge_hashtag_to_media_Edge> edges) {
            this.edges = edges;
        }
    }

    public static class Edge_hashtag_to_media_Edge{
        private Edge_hashtag_to_media_EdgeNode node;

        public Edge_hashtag_to_media_EdgeNode getNode() {
            return node;
        }

        public void setNode(Edge_hashtag_to_media_EdgeNode node) {
            this.node = node;
        }
    }

    public static class Edge_hashtag_to_media_EdgeNode{
        private String id;
        private String shortcode;
        private String display_url;
        private Edge_liked_by edge_liked_by;
        private boolean is_video;
        private Edge_media_to_caption edge_media_to_caption;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShortcode() {
            return shortcode;
        }

        public void setShortcode(String shortcode) {
            this.shortcode = shortcode;
        }

        public String getDisplay_url() {
            return display_url;
        }

        public void setDisplay_url(String display_url) {
            this.display_url = display_url;
        }

        public Edge_liked_by getEdge_liked_by() {
            return edge_liked_by;
        }

        public void setEdge_liked_by(Edge_liked_by edge_liked_by) {
            this.edge_liked_by = edge_liked_by;
        }

        public boolean isIs_video() {
            return is_video;
        }

        public void setIs_video(boolean is_video) {
            this.is_video = is_video;
        }
    }


    public static class Edge_media_to_caption{
        private List<Edge_media_to_captionEdge> edges;

        public List<Edge_media_to_captionEdge> getEdges() {
            return edges;
        }

        public void setEdges(List<Edge_media_to_captionEdge> edges) {
            this.edges = edges;
        }
    }
    public static class Edge_media_to_captionEdge{
        private Edge_media_to_captionEdgeNode node ;

        public Edge_media_to_captionEdgeNode getNode() {
            return node;
        }

        public void setNode(Edge_media_to_captionEdgeNode node) {
            this.node = node;
        }
    }
    public static class Edge_media_to_captionEdgeNode {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class Edge_liked_by{
        private Integer count;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
    public static class Page_info{
        private boolean has_next_page;
        private String end_cursor;

        public boolean isHas_next_page() {
            return has_next_page;
        }

        public void setHas_next_page(boolean has_next_page) {
            this.has_next_page = has_next_page;
        }

        public String getEnd_cursor() {
            return end_cursor;
        }

        public void setEnd_cursor(String end_cursor) {
            this.end_cursor = end_cursor;
        }
    }
}
