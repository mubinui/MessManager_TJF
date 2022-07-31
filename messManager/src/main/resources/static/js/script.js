const toggleSideBar = () => {
    if($(".sidebar").is(":visible")){

        $(".sidebar").css("display","none");
        $(".content").css("margin-left","0%");

    }else{
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","18%");
    }
};

// for modal

var modal = document.getElementById("myModal");

var btn = document.getElementById("myBtn");

var span = document.getElementsByClassName("close")[0];

// + opening the modal
btn.onclick = function() {
    modal.style.display = "block";
}

// X button-> close
span.onclick = function() {
    modal.style.display = "none";
}

//clicking outside the box
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}