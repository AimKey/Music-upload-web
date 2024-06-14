<%-- Document : index Created on : Jun 8, 2024, 5:40:45â¯PM Author : phamm --%> 
<%@page import="Model.SongDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.tomcat.util.codec.binary.Base64"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="java.io.File,Model.Song"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Hello world</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="assets/css/bootstrap.css" />
        <link rel="stylesheet" href="assets/css/style.css" />
        <!-- Put your script last to load the other scripts first -->
        <script
            src="https://cdnjs.cloudflare.com/ajax/libs/jsmediatags/3.9.5/jsmediatags.min.js"
            referrerpolicy="no-referrer"
        ></script>
        <script src="js/scripts.js"></script>
    </head>
    <body>

        <%
            session.setAttribute("contextPath", request.getContextPath());
        %>
        <div class="container-fluid justify-content-center text-center">
            <h1>
                This one is about uploading and dowloading files (In this case music) both from server and
                client
            </h1>
            <h2>Context path: ${contextPath}</h2>
            <form action="action">
                <label for="song">Upload a music file</label>
                <br />
                <input type="file" name="song" id="song" onchange="onMusicChanged(this)" />
            </form>
            <br />
            <h2>Here is your song (This one is for user upload):</h2>
            <div class="song mb-3">
                <div class="row align-items-center justify-content-center gap-1">
                    <div class="col-md-5">
                        <img id="img" class="currentSongImg" alt="">
                    </div>
                    <div class="col-md-5">
                        <ul class="list-group">
                            <li class="list-group-item list-group-item-primary">
                                <p>Title: <span class="title"></span></p>
                            </li>
                            <li class="list-group-item list-group-item-primary">
                                <p>Artists: <span class="artist"></span></p>
                            </li>
                            <li class="list-group-item list-group-item-secondary">
                                <p>Album: <span class="album"></span></p>
                            </li>
                            <li class="list-group-item list-group-item-info">
                                <p>Duration: <span class="duration"></span></p>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <h3>Here is your audio</h3>
                <br />
                <audio id="audio-controls" src="" controls></audio>
            </div>

            <div class="row mb-5">
                <h3>Upload music file to database here</h3>
                <%
                    String msg = (String) request.getAttribute("status");
                    if (msg != null) {
                        out.println("<h3>" + msg + "</h3>");
                    } else {
                        out.println("<p>Choose a song</p>");
                    }
                %>
                <form action="upload" method="post" enctype="multipart/form-data">
                    <input type="file" name="song" id="song" onchange="onMusicChanged(this)" />
                    <button class="btn btn-primary" type="submit">Send to server</button>
                </form>
            </div>

            <%
                // This one is a little bit inefficient, because it has to make query whenever this page is loaded/refreshed
                ArrayList<Song> songs = new SongDao().getAll();
                request.setAttribute("songs", songs);
            %>    

            <c:if test="${songs != null}">

                <div class="row mb-5 gap-1 justify-content-center">
                    <c:forEach var="s" items="${songs}">
                        <div class="col-md-5 col-lg-3">
                            <div class="card h-100 justify-content-between">
                                <img class="card-img-top card-song-img" src="${s.songFolder}${title}.jpeg" alt=${s.title} image>
                                <div class="card-body">
                                    <div class="card-header scroll-container">
                                        <p data-overflow="0">${s.title}</p>
                                    </div>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item scroll-container">
                                            <p data-overflow="0">Artists: ${s.artists}</p>
                                        </li>
                                        <li class="list-group-item scroll-container">
                                            <p data-overflow="0">Album: ${s.album}</p>
                                        </li>
                                        <li class="list-group-item scroll-container">
                                            <p data-overflow="0">Song duration: ${s.duration}</p>
                                        </li>
                                        <li class="list-group-item scroll-container">
                                            <p data-overflow="0">Hidden id: ${s.id}</p>
                                        </li>
                                        <li class="list-group-item">
                                            <button class="btn btn-primary mb-1" onclick="playSong('${s.songFolder}${title}.mp3', this)">Play this song</button>
                                        </li>
                                        <li class="list-group-item">
                                            <button class="btn btn-danger">Delete this song</button>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <%
                // Retrieve the song metadata from the request scope
                Song s = (Song) request.getAttribute("song");
            %>

            <c:if test="${song != null}" >
                <h2>Here is your song (This one is for song get from server):</h2>
                <div class="song mb-5 row align-items-center justify-content-center gap-1">

                    <div class="col-md-5">
                        <img class="border rounded"
                             style="width:100%; height: auto" alt="Song image here"/>
                    </div>
                    <div class="col-md-5">
                        <ul class="list-group">
                            <li class="list-group-item list-group-item-primary">
                                <p>Title: <span class="Title"><%= s.getTitle()%></span></p>
                            </li>
                            <li class="list-group-item list-group-item-primary">
                                <p>Artists: <span class="artist"><%= s.getArtists()%></span></p>
                            </li>
                            <li class="list-group-item list-group-item-secondary">
                                <p>Album: <span class="album"><%= s.getAlbum()%></span></p>
                            </li>
                            <li class="list-group-item list-group-item-info">
                                <p>Duration: <span class="duration"><%= s.getDuration()%></span></p>
                            </li>
                        </ul>
                    </div>
                </div>


                <div class="row mb-5">
                    <h3>Here is your audio</h3>
                    <br />
                    <audio src="${contextPath}/songs/<%= s.getTitle()%>.mp3" controls></audio>
                </div>
            </c:if>

        </div>
    </body>
</html>
