### [chapter2. 스프링 코어]
> IoC<sup>Inversion of Control</sup>(제어의 역전)은 스프링 프레임워크의 심장부라고 할 수 있다.      
> IoC 컨테이너는 POJO<sup>Plain Old Java Object</sup>(오래된 방식의 단순 자바 객체)를 구성하고 관리 한다.       
> 스프링 프레임워크의 가장 중요한 의의가 POJO로 자바 애플리케이션을 개발하는 것 이므로 스프링의 주요 기능은 대부분 IOC 컨테이너 안에서 POJO를 구성 및 관리하는 일과 연관 되어있다.

#### 2-1 자바로 POJO 구성하기

##### 애너테이션을 스캐닝하는 필터로 IoC 컨테이너 초기화 하기
> 기본적으로 스프링은 @Configuration, @Bean, @Component, @Repository, @Service, @Controller가 달린 클래스를 모두 감지한다.      
> 하나 이상의 포함/제외 필터를 적용해서 스캐닝 과정을 커스터마이징<sup>customizing</sup>(맞춤)할 수 있다.       
> 자바 패키지만 수십, 수백 개 달할 때 아주 유용한 기법이다.     


> 스프링이 지원하는 필터 표현식은 네 종류 이다.        
> annotaiton, assignable은 각각 필터 대상 애너테이션 타입 및 클래스/인터페이스를 지정하며 regex, aspectj는 각각 정규 표현식과 AspectJ 포인트컷 표현식으로 클래스를 매치하는 용도로 쓰인다.      
> use-default-filters속성으로 기본 필터를 해제할 수도 있다.

#### 2-2 생성자 호출해서 POJO 생성하기
> POJO 클래스에 생성자를 하나 이상 정의 한다.       
> 자바 구성 클래스에서 IoC 컨테이너가 사용할 POJO 인스턴트 값을 생성자로 설정한 다음, IoC 컨테이너를 인스턴스화해서 애너테이션을 붙인 자바 클래스를 스캐닝 하도록 한다.     
> 그러면 POJO 인스턴스/빈을 마치 애플리케이션 일부처럼 액세스할 수 있다.

#### 2-3 POJO 레퍼런스와 자동 연결을 이용해 다른 POJO와 상호 작용하기
> 자바 구성 클래스에 정의된 POJO/빈 인스턴스들 사이의 참조 관계는 표준 자바 코드로도 맺어줄 수 있다.       
> 필드, 세터 메서드, 생성자, 또는 다른 아무 메서드에 @Autowired를 붙이면 POJO 레퍼런스를 자동 연결해 쓸 수 있다.

##### @Autowired로 POJO 메서드와 생성자를 자동 연결하기, 자동 연결을 선택적으로 적용하기
> @Autowired는 POJO 세터 메서드에 직접 적용 할수 있다.     
> 스프링은 기본적으로 @Autowired를 붙인 필수 프로퍼티에 해당하는 빈을 찾지 못하면 예외를 던진다.        
> 따라서 선택적인 프로퍼티는 @Autowired의 required 속성값을 false로 지정해 스프링이 빈을 못 찾더라도 그냥 지나치게 한다.

> @Import로 구성 파일을 나누어 임포트 하는 방법도 있다.     

#### 2-4 @Resource와 @Inject를 붙여 POJO를 자동 연결하기
> @Autowired는 스프링 프레임워크, 구체적으로 org.springframework.beans.factory.annotation 패키지에 속해 있어서 스프링에서만 사용할 수 있다.     
> 자바에서도 동일한 기능의 애너테이션을 여럿 표준화 했다. javax.annotation.Resource와 javax.inject.Inject이 그렇다.

##### @Resource로 POJO 자동 연결하기
> 타입으로 POJO를 찾아 자동 연결하는 기능은 @Resource나 @Autowired나 마찬가지 이다.     
> 하지만 타입이 같은 POJO가 여럿일 때 @Autowired를 쓰면 가리키는 대상이 모호해 진다.        
> 결국 @Qualifier를 써서 이름으로 다시 POJO를 찾아야 하는 불편함이 있으므로     
> @Resource는 @Autowired와 @Qualifier를 합한 것과 같으므로 대상이 명확하다.

##### @Inject로 POJO 자동 연결하기
> @Inject도 일단 타입으로 POJO를 찾는다.     
> @Resource나 @Autowired와 같이 타입이 같은 POJO가 여럿일땐 다른 방법을 구사 해야 한다.
> @Inject를 이용해 이름으로 자동 연결을 하려면 먼저 POJO 주입 클래스와 주입 지점을 구별하기 위해 커스텀 애너테이션<sup>custom annotation</sup>(사용자가 제작한 애너테이션)을 작성해야 한다.
> 커스텀 애너테이션에 붙인 @Qualifier는 스프링에서 쓰는 @Qualifier와는 전혀 다른, @Inject와 동일 패키지(javax.inject)에 속한 애너테이션 이다.

```markdown
@Autowired, @Resource, @Ingect 셋중 어느걸 쓰더라고 결과는 같다.
@Autowired는 스프링에 @Resource, @Ingect는 자바 표중에(JSR)에 근거한 해법이라는 차이만 있다.
이름을 기준으로 할 경우에는 가장 구문이 단순한 @Resource가 낫고, 타입을 기준으로 하면 셋 중 아무거나 골라 간편하게 쓸 수 있다.
```

#### 2-5 @Scope를 붙여 POJO를 자동 연결하기
> @Scope는 빈<sup>bean</sup> 스코프를 지정하는 애너테이션 이다.
> 스프링은 IoC 컨테이너에 선언한 빈마다 정확히 인스턴스 하나를 생성하고 만들어진 인스턴스 전체 컨테이너 스코프에 공유 된다.
> getBean() 메서드를 호출하거나 빈을 참조하면 이러한 유일무이한 인스턴스가 반환된다는 것이다. 이 스코프가 모든 빈의 기본 스코프인 singleton이다.

| 스코프        | 설명                                                                   |
|---------------|---------------------------------------------------------------------|
| singletion    | IoC 컨테이너당 빈 인스턴스를 하나 생성한다.                                   |
| prototype     | 요청할 때마다 빈 인스턴스를 새로 만든다.                                      |
| request       | HTTP 요청당 하나의 빈 인스턴스를 생성한다. 웹 애플리케이션 컨텍스트에만 해당된다.      |
| session       | HTTP 세션당 빈 인스턴스 하나를 생성한다. 웹 애플리케이션 컨텍스트에만 해당된다        |
| globalSession | 전역 HTTP 세션당 빈 인스턴스 하나를 생성한다. 포털 애플리케이션 컨텍스트에만 해당된다. |

#### 2-6 외부 리소스(텍스트, XML, 프로퍼티, 이미지 파일)의 데이터 사용하기
> 스프링이 제공하는 @PropertySource를 이용하면 빈 프로퍼티 구성용 .properties 파일(키=값 쌍)을 읽어들일 수 있다. 
> Resource라는 단일 인터페이스를 사용해 어떤 유형의 외부 리소스라도 경로만 지정하면 가져올 수 있는 리소스 로드 메커니즘이 있고, 
> @Value로 접두어를 달리 하여 상이한 위치에 존재하는 리소스를 불러올 수도 있다.
> 파일시스템 리소스는 file, 클래스패스에 있는 리소스는 classpath 접두어로 붙이는 식이다. 리소스 경로는 URL로도 지정할 수 있다.
> @PropertySource를 붙여 프로퍼티 파일을 로드하려면 PropertySourcesPlaceholerConfigurer 빈을 @Bean으로 선언해야 한다.
> 스프링은 .properties 파일을 자동으로 연결 하므로 이 파일에 나열된 프로퍼티를 빈 프로퍼티로 활용할 수 있다.
> @Value에 자리끼움<sup>placeholder</sup>(치환자, 플레이스홀더) 표현식을 넣어 프로퍼티값을 자바 변수에 할당 한다.
> @Value("${key:default_value}") 구문으로 선언하면 읽어들인 애플리케이션 프로퍼티값으로 할당하고 키를 찾지 못하면 기본값(default_value)을 할당한다.

#### 2-7 프로퍼티 파일에서 로케일마다 다른 다국어 메세지를 해석하기
> MessageSource 인터페이스에는 리소스 번들<sup>resource bundle</sup> 메시지를 처리하는 메서드가 몇 가지 정의 되어있다. 
> ResourceBundleMessageSource는 가장 많이 쓰이는 MessageSource 구현체로, 로케일별로 분리된 리소스 번들 메시지를 해석한다.
> ResourceBundleMessageSource POJO를 구현하고 자바 구성 파일에서 @Bean을 붙여 선언하면 애플리케이션에서 필요한 i18n 데이터를 가져다 쓸 수 있다.

#### 2-8 애너테이션을 이용해 POJO 초기화/폐기 커스터마이징하기
> @Bean 정의부에서 initMethod, destroyMethod 속성을 설정하면 스프링은 이들을 각각 초기화, 폐기 콜백 메서드로 인지한다.
> POJO 메서드에 각각 @PostConstruct 및 @PreDestory를 붙여도 마찬가지 이다.
> 스프링에서는 @Lazy를 붙여 이른바 느긋한 초기화<sup>lazy initialization</sup>(주어진 시점까지 빈 생성을 미루는 기법)를 할 수 있고 @DependsOn으로 빈을 생성하기 전에 다른 빈을 먼저 생성하도록 강제할 수 있다.

#### 2-9 후처리기를 만들어 POJO 검증/수정하기
> 빈 후처리기<sup>bean post-processor</sup>를 이용하면 초기화 콜백 메서드(@Bean의 initMethod 속성에 지정한 메서드나 @PostConstruct를 붙인 메서드) 전후에 원하는 로직을 빈에 적용할 수 있다.
> 빈 후처리기의 가장 주요한 특징은 IoC 컨테이너 내부의 모든 빈 인스턴스를 대상으로 한다는 점이다.
> 빈 프로퍼티가 올바른지 체크하거나 어떤 기준에 따라 빈 프로퍼티를 변경 또는 전체 빈 인스턴스를 상대로 어떤 작업을 수행하는 용도로 쓰인다.
> @Required는 스프링에 내장된 후처리기 RequiredAnnotationBeanPostProcessor가 지원하는 애너테이션 이다.
> RequiredAnnotationBeanPostProcessor 후처리기는 @Required를 붙인 모든 빈 프로퍼티가 설정 되었는지 확인한다.

#### 2-10 팩토리(정적 메서드, 인스턴스 메서드, 스프링 FactoryBean)로 POJO 생성하기
> @Bean 메서드는 (일반 자바 구문으로) 정적 팩토리를 호출하거나 인스턴스 팩토리 메서드를 호출해서 POJO를 생성할 수 있다.
> 스프링은 FactoryBean 인터페이스를 상속한 간편한 템플릿 클래스 AbstractFactoryBean을 제공한다.

#### 2-11 스프링 환명 및 프로파일마다 다른 POJO 로드하기
> 자바 구성 클래스를 여러 개 만들고 각 클래스마다 POJO 인스턴스/빈 묶는다. 묶은 의도를 잘 표현할수 있게 프로파일을 명명하고 자바 구성 클래스에 @Profile를 붙인다.
> 애플리케이션 컨텍스트 환경을 가져와 프로파일을 설정하여 해당 POJO들을 가져오면 된다.

#### 2-12 POJO에게 IoC 컨테이너 리소스 알려주기
> 빈이 IoC 컨테이너 리소스를 인지하게 하려면 Aware(인지) 인터페이스를 구현한다. 스프링은 이 인터페이스를 구현한 빈을 감지해 대상 리소스를 세터 메서드로 주입한다.

* 자주 쓰는 스프링 Aware 인터페이스
| Aware 인터페이스                 | 대상 리소스 타입                                                                |
|----------------------------------|--------------------------------------------------------------------------|
| BeanNameAware                    | IoC 컨테이너에 구성한 인스턴스의 빈 이름                                               |
| BeanFactoryAware                 | 현재 빈 팩토리. 컨테이너 서비스를 호출하는 데 쓰임                                     |
| ApplicateionContextAware         | 현재 애플리케이션 컨텍스트, 켄터이너 서비스를 호출하는 데 쓰임                         |
| MessageSourceAware               | 메시지 소스. 텍스트 메시지를 해석하는 데 쓰임                                          |
| ApplicateionEvent PublisherAware | 애플리케이션 이벤트 발행기(publisher). 애플리케이션 이벤트를 발행하는 데 쓰임          |
| ResourceLoaderAware              | 리소스 로더. 외부 리소스를 로드하는 데 쓰임                                            |
| EnvironmentAware                 | ApplicationContext 인터페이스에 묶인 org.springframework.core.env.Environment 인스턴스 |

* Aware 인터페이스의 세터 메서드는 스프링 빈 프로퍼티를 설저안 이후, 초기화 콜백 메서드를 호출하기 이전에 호출한다.
1. 생성자나 팩토리 메서드를 호출해 빈 인스턴스를 생성
2. 빈 프로퍼티에 값, 빈 레퍼런스를 설정
3. Aware 인터페이스에 정의한 세터 메서드를 호출
4. 빈 인스턴스를 각 빈 후처리기에 있는 postProcessBeforeInitialization() 메서드로 넘겨 초기화 콜백 메서드를 호출
5. 빈 인스턴스를 각 빈 후처리기 postProcessAfterInitialization() 메서드로 넘김, 빈을 사용할 준비가 끝남
6. 컨테이너가 종료되면 폐기 콜백 메서드를 호출

```markdown
Aware 인터페이스를 구현한 클래스는 스프링과 엮이게 되므로 IoC 컨테이너 외부에서는 제대로 작동하지 않는다
```

#### 2-13 애너페이션을 활용해 애스펙트 지향 프로그래밍하기
> 애스펙트<sup>aspect</sup>를 정의하려면 일단 자바 클래스에 @Aspect를 붙이고 메서드벼로 



> 스프링은 기본적으로 @Autowired를 붙인 필수 프로퍼티에 해당하는 빈을 찾지 못하면 예외를 던진다. 따라서 선택적인 프로퍼티는 @Autowired의 required 속성값을 false로 지정해 스프링이 빈을 못 찾더라도 그냥 지나치게 한다.

##### 애너테이션으로 모호한 자동 연결 명시하기
> 타입을 기준으로 자동 연결하면 IoC 컨테이너에 호환 타입이 여럿 존재하거나 프로퍼티가(배열, 리스트, 맵 등의) 그룹형이 아닐 경우 제대로 연결 되지 않는다. 타입이 같은 빈이 여럿이라면 @Primary, @Qualifier로 해결 할 수 있다.

##### @Primary로 모호한 자동 연결 명시하기
> 스프링에서는 @Primary를 붙여 후보<sup>candidate</sup> 빈을 명시한다. 여러 빈이 자동 연결 대상일때 특정한 빈에 우선권을 부여 한다.     
> 빈 인스턴스가 여럿이라도 스프링은 @Primary를 붙인 클래스의 빈 인스턴스를 자동 연결한다.

##### @Qualifier로 모호한 자동 연결 명시하기
> @Qualifier에 이름을 주어 후보 빈을 명시할 수도 있다.      
> 스프링 IoC 컨테이너에서 명시한 이름의 빈을 찾아 해당 프로퍼티에 연결 한다.        
> @Qualifier는 메서드 인수를 연결하는 쓰임새도 있다.        
> @Resoure를 세터 메서드, 생성자, 필드에 붙이면 빈 프로퍼티를 이름으로 자동 연결 할수 있다.

##### 여러 곳에 분산된 POJO 참조 문제 해결하기
> 애플리케이션 규모가 커질수록 모든 POJO 설정을 하나의 자바 구성 클래스에 담아두기 어렵기 때문에 보통 POJO 기능에 따라 여러 자바 구성 클래스로 나누어 관리한다.     
> 자바 구성 클래스가 여럿 공존하면 상이한 클래스에 정의된 POJO를 자동 연결하거나 참조하는 일이 생각보다 그리 간단하지 않다.     
> 자바 구성 클래스가 위치한 경로마다 애플리케이션 컨텍스트를 초기화 하는 것이다. 각 자바 구성 클래스에 선언된 POJO를 컨텍스트와 레퍼런스로 읽으면 POJO 간 자동 연결이 가능하다.     