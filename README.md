# Web-Container
This is a simple Web container where handles HTTP requests(POST,GET) and returns a response. Sockets and threads are used to make sure it works for multiple clients.
> this web container simulates Tomcat and Servlet
## adding a path
- to add a path go to `resources/config.properties`
- add your path and assign it to your Servlet class with the package name.
- if your class name was `package.name.Hello` and your url pattern was `/greeting` then it will be like this `/greeting=package.name.Hello`
