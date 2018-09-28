# JavaHTTPSockets_Assgn1

need to check :

 1. Header not apprearing for http post
 2. json not present in data for http post
 3. URL redirection
 


Test :
 
httpc help

httpc help get

httpc help post


httpc get 'http://httpbin.org/get?course=networking&assignment=1'

httpc get -v 'http://httpbin.org/get?course=networking&assignment=1'

httpc get -v 'https://www.google.co.uk/intl/en/policies/privacy/'

Request header data:

---------------------

httpc get -v -h Content-Type:application/json 'http://httpbin.org/get?course=networking&assignment=1'

httpc post -h Content-Type:application/json -d {"Assignment": 1} 'http://httpbin.org/post'

httpc post -v -h Content-Type:application/json -d {"Assignment": 1,"course": "networking"} 'http://httpbin.org/post'

httpc post -h Content-Type:application/json,User-Agent:Mozilla/5.0 -d {"Assignment": 1} 'http://httpbin.org/post'

httpc post -v -h Content-Type:application/json,User-Agent:Mozilla/5.0 -d {"Assignment": 1} 'http://httpbin.org/post'