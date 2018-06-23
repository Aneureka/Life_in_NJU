<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 2017/5/24
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<body>
<div id="disqus_thread"></div>
<script>
  /**
   *  RECOMMENDED CONFIGURATION VARIABLES: EDIT AND UNCOMMENT THE SECTION BELOW TO INSERT DYNAMIC VALUES FROM YOUR PLATFORM OR CMS.
   *  LEARN WHY DEFINING THESE VARIABLES IS IMPORTANT: https://disqus.com/admin/universalcode/#configuration-variables
   */
   var disqus_config = function () {
   this.page.url = 'http://localhost:8080/stockinfo';  // Replace PAGE_URL with your page's canonical URL variable
//   this.page.identifier = "e94d73ff-fd92-467d-b643-c86889f4b8be"; // Replace PAGE_IDENTIFIER with your page's unique identifier variable
   };

  (function() {  // REQUIRED CONFIGURATION VARIABLE: EDIT THE SHORTNAME BELOW
    var d = document, s = d.createElement('script');

    s.src = 'https://kevin807210561.disqus.com/embed.js';  // IMPORTANT: Replace EXAMPLE with your forum shortname!

    s.setAttribute('data-timestamp', +new Date());
    (d.head || d.body).appendChild(s);
  })();
</script>
<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript" rel="nofollow">comments powered by Disqus.</a></noscript>
</body>
</html>
