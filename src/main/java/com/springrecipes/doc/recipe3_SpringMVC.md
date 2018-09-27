### [chapter3. 스프링 MVC]
> 스프링 프레임워크의 주요 모듈인 MVC는 강력한 IoC 컨테이너를 기반이다.        
> MVC<sup>Model-View-Controller</sup>(모델-뷰-컨트롤러)는 아주 일반적인 UI 디자인 패턴이다.

#### 3-1 간단한 스프링 MVC 웹 애플리케이션 개발하기
> 프론트 컨트롤러<sup>front controller</sup>는 스프링 MVC의 중심 컴포넌트 이다.     
> 보통 디스패처 서블릿<sup>dispatcher servlet</sup>이라고 하는 스프링 MVC 컨트롤러는 코어 자바 EE 다자인 패턴 중 하나인 
프론트 컨트롤러 패턴을 구현, MVC 프레임워크에서 모든 웹 요청은 반드시 디스패처 서블릿을 거쳐 처리된다.

