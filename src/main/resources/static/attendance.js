/*Grefiel, Micheree Kian M.*/

/*
This code controls the attendance page. It gets the event ID and course ID from
the page URL, then loads the list of students for that event. When the page
opens, it shows the event name and displays all students in a table. You can
edit a student’s name, mark them as Present or Absent for AM and PM, add new
students, or remove existing ones. Every change is sent to the server using
fetch, and the table is updated automatically so you don’t need to refresh
the page.
*/



const params = new URLSearchParams(window.location.search);
const eventId  = params.get("event_id");
const courseId = params.get("course_id");

loadAttendance();

function loadAttendance() {
    fetch(`/attendance?event_id=${eventId}&course_id=${courseId}`)
        .then(res => res.json())
        .then(data => {

            document.getElementById("eventName").innerText = data.event_name;
            const courseNameEl = document.getElementById("courseName");
            if (courseNameEl) courseNameEl.innerText = "";

            const body = document.getElementById("attendanceBody");
            body.innerHTML = "";

            data.students.forEach(s => {
                body.innerHTML += `
                <tr>
                    <td>${s.student_number}</td>
                    <td>
                        <input id="name-${s.student_id}" value="${s.fullname}">
                        <button onclick="saveName(${s.student_id})">Save</button>
                    </td>
                    <td>${s.year_level}</td>

                    <td>
                        <select onchange="saveStatus(${s.student_id}, 'AM', this.value)">
                            <option value="Present" ${s.status_am === 'Present' ? 'selected' : ''}>Present</option>
                            <option value="Absent" ${s.status_am === 'Absent' ? 'selected' : ''}>Absent</option>
                        </select>
                    </td>

                    <td>
                        <select onchange="saveStatus(${s.student_id}, 'PM', this.value)">
                            <option value="Present" ${s.status_pm === 'Present' ? 'selected' : ''}>Present</option>
                            <option value="Absent" ${s.status_pm === 'Absent' ? 'selected' : ''}>Absent</option>
                        </select>
                    </td>

                    <td>
                        <button onclick="removeStudent(${s.student_id})">Remove</button>
                    </td>
                </tr>`;
            });
        });
}

function addStudent() {
    fetch("/attendance/add", {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify({
            eventId: eventId,
            courseId: courseId, // ✅ REQUIRED
            studentNumber: document.getElementById("studentNumber").value,
            fullName: document.getElementById("fullName").value,
            yearLevel: document.getElementById("yearLevel").value
        })
    }).then(() => loadAttendance());
}

function removeStudent(id) {
    fetch("/attendance/delete", {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify({
            eventId: eventId,
            studentId: id
        })
    }).then(() => loadAttendance());
}

function saveName(id) {
    fetch("/attendance/edit-name", {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify({
            studentId: id,
            fullName: document.getElementById(`name-${id}`).value
        })
    }).then(() => loadAttendance());
}

function saveStatus(studentId, period, status) {
    fetch("/attendance/update-status", {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify({
            eventId: eventId,
            studentId: studentId,
            period: period,
            status: status
        })
    });
}