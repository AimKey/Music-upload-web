//IMPORTANT: Install jsmedia tags first
// Go to cdnjs and just grab that script file and put into our <head></head>
const jsmediatags = window.jsmediatags;
const SONG_LOCATION = "/TestingUploadFiles/songs/";
let userAudio;

// This function will extract and set the audio tags to display
function onMusicChanged(evt) {
  const file = evt.files[0];
  userAudio = file;
  jsmediatags.read(file, {
    onSuccess: function (tag) {
      //   console.log(tag);
      const data = tag.tags.picture.data;
      const format = tag.tags.picture.format;
      let base64String = ""; //Convert array to base 64
      for (let i = 0; i < data.length; i++) {
        base64String += String.fromCharCode(data[i]);
      }
      document.querySelector(
        "#img"
      ).style.backgroundImage = `url(data:${format};base64,${window.btoa(base64String)})`;
      document.querySelector(".title").textContent = tag.tags.title;
      document.querySelector(".artist").textContent = tag.tags.artist;
      document.querySelector(".album").textContent = tag.tags.album;
    },
    onError: function (e) {
      alert(e);
    }
  });

  getAudio();
}

function convertSecsToMins(secs) {
  let minutes = Math.floor(secs / 60);
  let seconds = Math.floor(secs % 60);
  if (seconds < 10) {
    seconds = `0${seconds}`;
  }
  return `${minutes}:${seconds}`;
}

function getAudio() {
  if (userAudio) {
    const audioURL = URL.createObjectURL(userAudio);
    // console.log("Audio element:");
    let audioElement = new Audio(audioURL);
    audioElement.addEventListener("loadedmetadata", function () {
      //   console.log("Duration:", audioElement.duration);
      document.querySelector(".duration").textContent = convertSecsToMins(audioElement.duration);
    });

    let audioPlayer = document.querySelector("#audio__controls");
    audioPlayer.src = audioURL;

    // audioPlayer.play();
  } else {
    alert("No audio found");
  }
}

function handleAudioFromDB(obj) {
  const file = evt.files[0];
  userAudio = file;
  //   console.log("Get files:");
  //   console.log(file);
  jsmediatags.read(file, {
    onSuccess: function (tag) {
      //   console.log(tag);
      const data = tag.tags.picture.data;
      const format = tag.tags.picture.format;
      let base64String = ""; //Convert array to base 64
      for (let i = 0; i < data.length; i++) {
        base64String += String.fromCharCode(data[i]);
      }
      document.querySelector(
        "#img"
      ).style.backgroundImage = `url(data:${format};base64,${window.btoa(base64String)})`;
      document.querySelector(".title").textContent = tag.tags.title;
      document.querySelector(".artist").textContent = tag.tags.artist;
      document.querySelector(".album").textContent = tag.tags.album;
    },
    onError: function (e) {
      alert(e);
    },
  });

  getAudio();
}

function handlePlaySong(obj) {
  console.log(obj);
  console.log("Loading song information!");
  let option = obj.value;
  console.log("Use choosed this song: " + option);

  const audioURL = SONG_LOCATION + option;
  let file = new File();
  console.log(file);

  jsmediatags.read(file, {
    onSuccess: function (tag) {
      //   console.log(tag);
      const data = tag.tags.picture.data;
      const format = tag.tags.picture.format;
      let base64String = ""; //Convert array to base 64
      for (let i = 0; i < data.length; i++) {
        base64String += String.fromCharCode(data[i]);
      }
      document.querySelector(
        "#img"
      ).style.backgroundImage = `url(data:${format};base64,${window.btoa(base64String)})`;
      document.querySelector(".title").textContent = tag.tags.title;
      document.querySelector(".artist").textContent = tag.tags.artist;
      document.querySelector(".album").textContent = tag.tags.album;
    },
    onError: function (e) {
      alert(e);
    },
  });
}

function playSong(songFolder, obj) {
  console.log("Playing");
  console.log(obj);
  const card = obj.closest(".card"); // Get the closest parent card
  const imgSrc = card.querySelector(".card-img-top").src;
  const title = card.querySelector(".card-header").textContent;
  const artists = card.querySelector(".list-group-item:nth-child(1)").textContent.split(": ")[1];
  const album = card.querySelector(".list-group-item:nth-child(2)").textContent.split(": ")[1];
  const duration = card.querySelector(".list-group-item:nth-child(3)").textContent.split(": ")[1];
  const id = card.querySelector(".list-group-item:nth-child(4)").textContent.split(": ")[1];
  console.log(imgSrc);
  const audioControls = document.querySelector("#audio-controls");
  audioControls.src = songFolder;
  //  document.querySelector("#img").style.backgroundImage = `url(${imgSrc})`;

  const img = document.querySelector("#img");
  console.log(img);
  img.src = imgSrc;

  document.querySelector(".title").textContent = title;
  document.querySelector(".artist").textContent = artists;
  document.querySelector(".album").textContent = album;
  document.querySelector(".duration").textContent = convertSecsToMins(duration);
  audioControls.volume = 0.5;
  audioControls.play();
}
let count = 0;
document.addEventListener("DOMContentLoaded", function () {
  const scrollContainers = document.querySelectorAll(".scroll-container");
  scrollContainers.forEach((s) => {
    let scrollText = s.querySelector("p");
    if (scrollText) {
      const containerWidth = s.offsetWidth;
      const scrollTextWidth = scrollText.offsetWidth;
      //      console.log(
      //        `Text :${scrollText.textContent}, width inlucding border: ${scrollTextWidth}, parent width: ${containerWidth}`
      //      );
      // Text is greater than container.
      // Plus the padding also
      const overflowPixels = scrollTextWidth - containerWidth + 20;
      if (overflowPixels > 5) {
        // Add the scroll-text class to our text
        scrollText.classList.add("scroll-text");
        // Set the duration
        const scrollDuration = overflowPixels / 5;
        // Get the css (The first one is bootstrap)
        const styleSheet = document.styleSheets[1];
        // Generate our custom CSS frame
        const animationName = `scroll${count}`;
        count = count + 1;
        console.log(scrollText.textContent + " overflow pixels: " + overflowPixels);
        console.log("Animation name: " + animationName);
        const keyframes = `
                            @keyframes ${animationName} {
                                0% { transform: translateX(0); }
                                50% { transform: translateX(-${overflowPixels}px); }
                                100% { transform: translateX(0); }
                            }
                        `;
        // Insert the new rule
        styleSheet.insertRule(keyframes, styleSheet.cssRules.length);
        // Put the animation on our scroll-text
        scrollText.style.animation = `${animationName} ${scrollDuration}s linear infinite`;
      }
    }
  });
});
