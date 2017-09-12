<%--
  Created by IntelliJ IDEA.
  User: davide
  Date: 04/09/17
  Time: 15.42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Deep Web Data Extraction</title>

  <!-- Bootstrap Core CSS -->
  <link href="../css/bootstrap.css" rel="stylesheet">

  <!-- Custom CSS -->
  <link rel="stylesheet" href="../css/main.css">
  <link href="../css/custom.css" rel="stylesheet">

  <script src="//use.edgefonts.net/bebas-neue.js"></script>

  <!-- Custom Fonts & Icons -->
  <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,600,800' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="../css/icomoon-social.css">
  <link rel="stylesheet" href="../css/font-awesome.min.css">

  <script src="../js/modernizr-2.6.2-respond-1.1.0.min.js"></script>


</head>

<body>
<!--[if lt IE 7]>
<p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
<![endif]-->


<header class="navbar navbar-inverse navbar-fixed-top" role="banner">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <!--   <a class="navbar-brand" href="index.html"><img src="img/deepweb-logo.jpg" alt="Deep Web"></a>-->
    </div>
    <div class="collapse navbar-collapse">
      <ul class="nav navbar-nav navbar-right">
        <li class="active"><a href="index.html">Home</a></li>
        <li><a href="/searchAlbum">Search Album</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Pages <i class="icon-angle-down"></i></a>
          <ul class="dropdown-menu">
            <li><a href="#">Dropdown Menu 1</a></li>
            <li><a href="#">Dropdown Menu 2</a></li>
            <li><a href="#">Dropdown Menu 3</a></li>
            <li><a href="#">Dropdown Menu 4</a></li>
            <li><a href="#">Dropdown Menu 5</a></li>
            <li class="divider"></li>
            <li><a href="#">Privacy Policy</a></li>
            <li><a href="#">Terms of Use</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</header><!--/header-->




<section id="main-slider" class="no-margin">
  <div class="carousel slide">
    <ol class="carousel-indicators">
      <li data-target="#main-slider" data-slide-to="0" class="active"></li>
      <li data-target="#main-slider" data-slide-to="1"></li>
      <li data-target="#main-slider" data-slide-to="2"></li>
    </ol>
    <div class="carousel-inner">
      <div class="item active" style="background-image: url(../img/deep-web1.jpg)">
        <div class="container">
          <div class="row">
            <div class="col-sm-12">
              <div class="carousel-content centered">
                <h2 class="animation animated-item-1">Deep Web Data Extraction</h2>
                <p class="animation animated-item-2">Perchè il Deep Web? </p>
              </div>
            </div>
          </div>
        </div>
      </div><!--/.item-->
      <div class="item" style="background-image: url(../img/slides/2.jpg)">
        <div class="container">
          <div class="row">
            <div class="col-sm-12">
              <div class="carousel-content center centered">
                <h2 class="animation animated-item-1">Google ha indicizzato solo il 5% del web</h2>
                <p class="animation animated-item-2">La porzione nascosta del web contiene moltissime informazioni che possono essere estratte </p>
                <br>
                <a class="btn btn-md animation animated-item-3" href="#">Learn More</a>
              </div>
            </div>
          </div>
        </div>
      </div><!--/.item-->
      <div class="item" style="background-image: url(../img/slides/3.jpg)">
        <div class="container">
          <div class="row">
            <div class="col-sm-12">
              <div class="carousel-content centered">
                <h2 class="animation animated-item-1">Works Seamlessly Well on All Devices</h2>
                <p class="animation animated-item-2">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce vitae euismod lacus. Maecenas in tempor lectus. Nam mattis, odio ut dapibus ornare, libero. </p>
                <br>
                <a class="btn btn-md animation animated-item-3" href="#">Learn More</a>
              </div>
            </div>
          </div>
        </div>
      </div><!--/.item-->
    </div><!--/.carousel-inner-->
  </div><!--/.carousel-->
  <a class="prev hidden-xs" href="#main-slider" data-slide="prev">
    <i class="icon-angle-left"></i>
  </a>
  <a class="next hidden-xs" href="#main-slider" data-slide="next">
    <i class="icon-angle-right"></i>
  </a>
</section><!--/#main-slider-->

<!-- Footer -->
<div class="footer">
  <div class="container">

    <div class="row">
      <div class="col-footer col-md-4 col-xs-6">
        <h3>Our Social Networks</h3>
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam.</p>
        <div>
          <img src="../img/icons/facebook.png" width="32" alt="Facebook">
          <img src="../img/icons/twitter.png" width="32" alt="Twitter">
          <img src="../img/icons/linkedin.png" width="32" alt="LinkedIn">
          <img src="../img/icons/rss.png" width="32" alt="RSS Feed">
        </div>
      </div>
      <div class="col-footer col-md-4 col-xs-6">
        <h3>About Our Company</h3>
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci.</p>
      </div>

    </div>
    <div class="row">
      <div class="col-md-12">
        <div class="footer-copyright"></div>
      </div>
    </div>
  </div>
</div>

<!-- Javascripts -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../js/jquery-1.9.1.min.js"><\/script>')</script>
<script src="../js/bootstrap.min.js"></script>

<!-- Scrolling Nav JavaScript -->
<script src="../js/jquery.easing.min.js"></script>
<script src="../js/scrolling-nav.js"></script>

</body>
</html>
