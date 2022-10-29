// $(async function() {
//     await allUsers();
// });
// const table = $('#tableUsers');
// async function allUsers() {
//     table.empty();
//     fetch("http://localhost:8080/users")
//         .then(res => res.json())
//         .then(data => {
//             data.forEach(user => {
//                 let tableWithUsers = `$(
//                         <tr>
//                             <td>${user.id}</td>
//                             <td>${user.firstName}</td>
//                             <td>${user.lastName}</td>
//                             <td>${user.username}</td>
//                             <td>${user.roles.map(role => " " + role.name)}</td>
//                             <td>
//                                 <button type="button" class="btn btn-info" data-toggle="modal" id="buttonEdit"
//                                 data-action="edit" data-id="${user.id}" data-target="#edit">Edit</button>
//                             </td>
//                             <td>
//                                 <button type="button" class="btn btn-danger" data-toggle="modal" id="buttonDelete"
//                                 data-action="delete" data-id="${user.id}" data-target="#delete">Delete</button>
//                             </td>
//                         </tr>)`;
//                 table.append(tableWithUsers);
//             })
//         })
// }

const requestURL = 'http://localhost:8080/users';

// const usersTableNavLink = document.getElementById("horizontal_navigation-users_table");

const allUsersTable = document.querySelector("all-users-table");


//Users table

const fillUsers = (users) => {
    if (users.length > 0) {
        let temp = '';
        users.forEach((user) => {
            temp += `
                <tr>
                    <td> ${user.id} </td>
                    <td> ${user.firstName} </td>
                    <td> ${user.lastName} </td>
                    <td> ${user.username} </td>
                    <td> ${user.age} </td>
                    <td> ${user.roles.map((role) => role.name === "USER" ? " USER" : " ADMIN")} </td>
                    <td> 
                        <button type="button" class="btn btn-info" id="btn-edit-modal-call" data-toggle="modal" data-target="modal-edit"
                            data-id="${user.id}">Edit</button>
                    </td>
                    <td> 
                        <button type="button" class="btn btn-danger" id="btn-delete-modal-call" 
                            data-id="${user.id}">Delete</button>
                    </td>
                </tr>
        `
        })
        allUsersTable.innerHTML = temp;
    }
}

function getAllUsers () {
    fetch(requestURL, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .then(data => {
            fillUsers(data);
        })
}

getAllUsers();