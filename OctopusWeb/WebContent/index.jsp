<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<title>OctopusWeb 1.0</title>
</head>
<body>
<!-- NavBar -->
<div class="row-fluid">
<div class="span12 well pricehover">
   <div class="navbar">
    <div class="navbar-inner">
      <div class="container">
        <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </a>
       	<a class="brand" href="#">WSNWeb 1.0</a>
        <div class="nav-collapse">
          <ul class="nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#">Link</a></li>
            <li><a href="#">Link</a></li>
            <li><a href="#">Link</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li class="nav-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
          <form class="navbar-search pull-left" action="" >
            <input type="text" class="search-query span12" placeholder="Search">
          </form>
          <ul class="nav pull-right">
            <li><a href="#">Link</a></li>
            <li class="divider-vertical"></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li><a href="#">Separated link</a></li>
              </ul>
            </li>
          </ul>
        </div><!-- /.nav-collapse -->
      </div>
    </div><!-- /navbar-inner -->
  </div><!-- /navbar -->
 </div>
 </div>
 
 
 <!-- Body -->
 <div class="container-fluid">	
	

	<div class="row-fluid">
		<div class="span8">
			<div class="row-fluid">
				<div class="span12" style="border:1px solid black">
					<canvas id="canvas" height="400"  width="750" ></canvas>
				</div>
			</div>
			<br>
			<div class="row-fluid" >
				<div class="span8">
					<textarea style="align:center" class="span12" rows="7" id="console" disabled="disabled"></textarea>
				</div>
				<div class="span4" id="consolecenter" style="border:1px solid black">
					
					<span class="label label-important">Message Received</span><input type="checkbox" id="MsgReceived" name="msgType" value="receive"/><br/><br/>
					  <span class="label label-important">Message Sent</span><input type="checkbox"  id="MsgSent" name="msgType" value="send"/><br/><br/>
					<span class="label label-important">Mote Added</span><input type="checkbox"  id="MoteAdded" name="msgType" value="add" checked/><br/><br/>
					<span class="label label-important">Mote Updated</span><input type="checkbox"  id="MoteUpdated" name="msgType" value="update"/><br/>
				</div>
			</div>
			
		</div>
		<div class="span4">
			<table class="table table-bordered table-striped">
				<tbody>
					<tr>
						<td>
							<div class="span6">
								<label id="moteid">No specific mote selected</label>
							</div>
							<div class="span6">
								<input type="checkbox" id="broadcast" /> Broadcast
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<label id="battery">Battery: 0%</label>
						</td>
					</tr>
					<tr>
						<td>
							Request Mode: <button class="btn btn-success"id="modeauto">Auto</button>  <button class="btn btn-success" id="modequery">Query</button>
						</td>
					</tr>
					
					<tr>
						<td>
							Sleep Mode:<button class="btn btn-success" id="sleep">To Sleep</button>  <button class="btn btn-success" id="awake">Wake Up</button>
						</td>
					</tr>
					<tr>
						<td>
							<div class="span5">
								<label> Sleep Duty Cycle:</label>
							</div>
							<div class="span1 ">
									<label id="sleeplabel">50</label>
							</div>
							<div class="span1 ">
									<label>%</label>
							</div>
							<div class="span5">
									<button class="btn btn-success" id="btnSleepDutyCycle">Apply</button>
							</div>	
							<div class="span12">
								Sleep Duty Cycle(in%) <input type="range" id="sleepdutycycle" min="0" max="100" step="1" />
							</div>
							
						</td>
					</tr>
					<tr>
						<td>
							<div class="span5">
								<label> Awake Duty Cycle:</label>
							</div>
							<div class="span1 ">
									<label id="awakelabel">50</label>
							</div>
							<div class="span1 ">
									<label>%</label>
							</div>
							<div class="span5">
									<button class="btn btn-success" id="btnAwakeDutyCycle">Apply</button>
							</div>	
							<div class="span12">
								Awake Duty Cycle(in%) <input type="range" id="awakedutycycle" min="0" max="100" step="1" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="span5">
								<label> Threshold:</label>
							</div>
							<div class="span1 ">
									<label id="thresholdlabel">50</label>
							</div>
							<div class="span1 ">
									<label>%</label>
							</div>
							<div class="span5">
									<button class="btn btn-success" id="btnThreshold">Apply</button>
							</div>	
							<div class="span12">
								Threshold(in%)  <input type="range" id="threshold" min="0" max="100" step="1" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="span7 ">
									<label>(Minimum is 1024ms) </label>
							</div>
							<div class="span5">
									<button class="btn btn-success" id="btnPeriodSampling">Apply</button>
							</div>	
							Period Sampling: <input type="number" id="periodsampling" />
						</td>
					</tr>
					<tr>
						<td>
							<button class="btn btn-success" id="btnReading">Read the sensor</button>
						</td>
					</tr>
					<tr>
						<td>
							<button class="btn btn-success" id="btnBootNetwork">Boot Network</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="row-fluid" >
		<div class="span12" id="Chart" style="height:400px;border:1px solid black" >
			
		</div>
	</div>
	
	<hr>
	<footer>
		<p>@ WSNWeb 1.0 2013</p>
	</footer>	
</div>





<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/chart/highcharts/highcharts.src.js"></script>
<script type="text/javascript" src="js/chart/highcharts/exporting.js"></script>
<script type="text/javascript" src="js/chart/highstock/highstock.src.js"></script>
<script type="text/javascript" src="js/table/jquery.dataTables.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>

</body>
</html>