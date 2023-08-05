const button = document.getElementById("btn");
const register = document.getElementById("login-form");

register.addEventListener("submit", function (e) {
    e.preventDefault();

    const payload = new FormData(register);

    // Convert FormData to JSON object
    const payloadJSON = {};
    for (const [key, value] of payload.entries()) {
        payloadJSON[key] = value;
    }

    console.log(payloadJSON);

    fetch("http://localhost:9090/api/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json", // Set the Content-Type header to application/json
        },
        body: JSON.stringify(payloadJSON), // Convert the JSON object to a string and use it as the request body
    })
        .then((response) => response.json())
        .then((data) => {
            console.log(data);

            if (!data.error) {
                window.location.href = "/";
            } else {
                alert(data.message);
            }

            }


        );


});

