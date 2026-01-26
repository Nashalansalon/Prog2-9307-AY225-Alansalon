// Define multiple users
const USERS = [
    { username: "wi", password: "1234" },
    { username: "nash", password: "1234" },
    { username: "don", password: "laway" },
    { username: "maricar", password: "maricakes" },
    { username: "naz", password: "nazcute" }
];

function login() {
    const userInput = document.getElementById("username").value;
    const passInput = document.getElementById("password").value;
    const result = document.getElementById("result");

    const now = new Date();
    const timestamp = now.toLocaleString();

    // Check if username/password matches any of the users
    const matchedUser = USERS.find(user => user.username === userInput && user.password === passInput);

    if (matchedUser) {
        result.textContent = "Login Successful! " + timestamp;
        result.style.color = "green";
        saveAttendance(userInput, timestamp, "SUCCESS");
    } else {
        playWrongBeep(); // Loud wrong beep sound
        result.textContent = "Incorrect Username or Password! " + timestamp;
        result.style.color = "red";
        saveAttendance(userInput, timestamp, "FAILED");
    }
}

// 🔊 Loud Wrong Beep Sound
function playWrongBeep() {
    const audio = new Audio ('laugh.wav');
    audio.play();
}


// 📄 Generate Attendance File (Download)
function saveAttendance(username, time, status) {
    const data =
        "Username: " + username + "\n" +
        "Time: " + time + "\n" +
        "Status: " + status + "\n" +
        "-----------------------------\n";

    const blob = new Blob([data], { type: "text/plain" });
    const link = document.createElement("a");

    link.href = URL.createObjectURL(blob);
    link.download = "attendance.txt";
    link.click();
}
