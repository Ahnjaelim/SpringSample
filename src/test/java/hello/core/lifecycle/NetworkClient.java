package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;  // 스프링 종속 기술이 아니라 자바 기술임
import javax.annotation.PreDestroy;

// 인터페이스는 스프링 초창기에나 썼던 방법
// public class NetworkClient implements InitializingBean, DisposableBean
public class NetworkClient {

    private  String url;

    public NetworkClient(){
        System.out.println("생성자 호출, url = "+url);
        connect();
        call("초기화 연결 메세지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("connect: "+url);
    }

    public void call(String message) {
        System.out.println("call:"+url+"message = " + message);
    }

    public void disconnect(){
        System.out.println("close:"+url);
    }

    @PostConstruct
    public void init() { // 의존관계가 끝나면 호출하겠다는 뜻
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() throws Exception {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
