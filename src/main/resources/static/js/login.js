const login = document.getElementById("login-form");

login.addEventListener("submit", function(e) {
    e.preventDefault();

    const payload = new FormData(login);

    const payloadJSON = {};
    for (const [key, value] of payload.entries()) {
        payloadJSON[key] = value;
    }

    console.log(payloadJSON);

    fetch("http://localhost:9090/api/auth/authenticate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(payloadJSON),
    })
        .then((response) => response.json())
        .then((data) => {
                console.log(data);

                const token = data.token;

                sessionStorage.setItem("token", token);

                if (!data.error) {
                    window.location.href = "/";
                } else {
                    alert(data.message);
                }

            }
        );
});