// $(async function() {
//     await newUser();
// });
// async function newUser() {
//     await fetch("http://localhost:8080/roles")
//         .then(res => res.json())
//         .then(roles => {
//             roles.forEach(role => {
//                 let el = document.createElement("option");
//                 el.text = role.name.substring(5);
//                 el.value = role.id;
//                 $('#newUserRoles')[0].appendChild(el);
//             })
//         })
//
//     const form = document.forms["formNewUser"];
//
//     form.addEventListener('submit', addNewUser)
//
//     function addNewUser(e) {
//         e.preventDefault();
//         let newUserRoles = [];
//         for (let i = 0; i < form.roles.length; i++) {
//             if (form.roles[i].selected) newUserRoles.push({
//                 id : form.roles[i].value,
//                 name : form.roles[i].name
//             })
//         }
//         fetch("http://localhost:8080/users", {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify({
//                 firstName: form.firstName,
//                 lastName: form.lastName,
//                 username: form.username,
//                 password: form.password,
//                 roles: newUserRoles
//             })
//         }).then(() => {
//             form.reset();
//             allUsers();
//             $('#allUsersTable').click();
//         })
//     }
// }
const usersTableNavLink = document.getElementById("allUsersTable");
const addUserForm = document.querySelector("add-user-form");
const requestURL = 'http://localhost:8080/users';
// form for NEW user

const FormFirstName = document.getElementById("fN");
const FormLastName = document.getElementById("lN");
const FormUsername = document.getElementById("email");
const FormPassword = document.getElementById("pw");
const FormAge = document.getElementById("ag");
const FormRoles = document.getElementById("newUserRoles");
// submit
const addButtonSubmit = document.getElementById("newUserButton");

//role
function getRolesFromAddUserForm() {
    let roles = Array.from(FormRoles.selectedOptions)
        .map(option => option.value);
    let rolesToAdd = [];
    if (roles.includes("1")) {
        let role1 = {
            id: 1,
            name: "ADMIN"
        }
        rolesToAdd.push(role1);
    }
    if (roles.includes("2")) {
        let role2 = {
            id: 2,
            name: "USER"
        }
        rolesToAdd.push(role2);
    }
    console.log(rolesToAdd);
    return rolesToAdd;
}

addUserForm.addEventListener("submit", (e) => {
    e.preventDefault();
    fetch(requestURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstName: FormFirstName.value,
            lastName: FormLastName.value,
            username: FormUsername.value,
            password: FormPassword.value,
            age: FormAge.value,
            roles: getRolesFromAddUserForm()
        })
    })
        .then(() => {
            usersTableNavLink.click();
            console.log(getRolesFromAddUserForm() + "----------------------------------------------------")
            location.reload();
        });
})
