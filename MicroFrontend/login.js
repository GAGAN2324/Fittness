const API_BASE = "http://localhost:8080";

function login() {

    const email =
        document.getElementById("email").value;

    const password =
        document.getElementById("password").value;

    if (!email || !password) {

        document.getElementById("message")
            .innerText =
            "Please enter email and password";

        return;
    }

    fetch(
        API_BASE + "/api/auth/login",
        {
            method: "POST",

            headers: {
                "Content-Type":
                    "application/json"
            },

            body: JSON.stringify({

                email: email,
                password: password
            })
        }
    )

    .then(response => {

        if (!response.ok) {

            throw new Error(
                "Invalid Credentials"
            );
        }

        // Backend now returns JSON: { token, userId, email, fullName }
        // (it used to return the token as raw text).
        return response.json();
    })

    .then(data => {

        localStorage.setItem(
            "token",
            data.token
        );

        localStorage.setItem(
            "fullName",
            data.fullName
        );

        window.location.href =
            "dashboard.html";
    })

    .catch(error => {

        document.getElementById("message")
            .innerText =
            "Invalid Email or Password";
    });
}
