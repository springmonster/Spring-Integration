package com.kuang.spring.integration.config;

import com.alibaba.fastjson.JSON;
import com.kuang.spring.integration.pojo.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class EsConfigTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    void createIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("khch_index_1");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexRequest);
    }

    @Test
    void queryIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("khch_index_1");
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    void deleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("khch_index_1");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete);
    }

    @Test
    void addDocument() throws IOException {
        User user = new User("khch", 10);

        IndexRequest indexRequest = new IndexRequest("khch_user_index");
        indexRequest.id("1");
        indexRequest.timeout(TimeValue.timeValueSeconds(1));
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);

        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.status());
    }

    @Test
    void getDocument() throws IOException {
        GetRequest getRequest = new GetRequest("khch_user_index", "1");

        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);

        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @Test
    void updateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("khch_user_index", "1");
        updateRequest.timeout(TimeValue.timeValueSeconds(1));

        User user = new User("khch", 33);
        updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);

        UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @Test
    void deleteDocument() throws IOException {
//        User user = new User("dan", 20);
//
//        IndexRequest indexRequest = new IndexRequest("khch_user_index");
//        indexRequest.id("2");
//        indexRequest.timeout(TimeValue.timeValueSeconds(1));
//        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
//
//        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
//        System.out.println(indexResponse.status());

        DeleteRequest deleteRequest = new DeleteRequest("khch_user_index", "2");
        deleteRequest.timeout(TimeValue.timeValueSeconds(1));

        DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @Test
    void searchDocument() throws IOException {
        SearchRequest searchRequest = new SearchRequest("khch_user_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "khch");

        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        String s = JSON.toJSONString(hits);

        System.out.println(s);

        for (int i = 0; i < hits.getHits().length; i++) {
            System.out.println(hits.getHits()[i].getSourceAsMap());
        }
    }
}