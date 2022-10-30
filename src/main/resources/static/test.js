const FormFirstName = document.getElementById("fN");
const FormLastName = document.getElementById("lN");
const FormUsername = document.getElementById("email");
const FormPassword = document.getElementById("pw");
const FormAge = document.getElementById("ag");
const FormRoles = document.getElementById("newUserRoles");
const usersTableNavLink = document.getElementById("allUsersTable");
const addUserForm = document.querySelector(".add-user-form");
const addButtonSubmit = document.getElementById("newUserButton");
const requestURL = 'http://localhost:8080/users';
const allUsersTable = document.querySelector(".all-users-table");

$(async function () {
    // await getUser();
    await infoUser();
    // await tittle();
    // await getUsers();
    // await getNewUserForm();
    // await getDefaultModal();
    // await createUser();

})
const userFetch = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    // findAllUsers: async () => await fetch('api/users'),
    findUserByUsername: async () => await fetch(`userAuth`),
    // findOneUser: async (id) => await fetch(`api/users/${id}`),
    // addNewUser: async (user) => await fetch('api/users', {method: 'POST', headers: userFetch.head, body: JSON.stringify(user)}),
    // updateUser: async (user, id) => await fetch(`api/users/${id}`, {method: 'PUT', headers: userFetch.head, body: JSON.stringify(user)}),
    // deleteUser: async (id) => await fetch(`api/users/${id}`, {method: 'DELETE', headers: userFetch.head})
}

async function infoUser() {
    let temp = '';
    const info = document.querySelector('#info');
    await userFetch.findUserByUsername()
        .then(res => res.json())
        .then(user => {
            temp += `
                    <span class="font-weight-bold">${user.username}</span>
                    <span class="font-weight-normal">with roles: ${user.roles.map(e => " " + e.name)}</span>       
            `;
            //Добавляем информацию в таблицу
            let userForPanel = `$(
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.username}</td>
                <td>${user.roles.map(e => " " + e.name)}</td>)`;
            $('#userPanelBody').append(userForPanel);
        });
    info.innerHTML = temp;
}
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
                        <button type="button" class="btn btn-info" id="btnEditCall" data-toggle="modal" data-target="modal-edit"
                            data-id="${user.id}">Edit</button>
                    </td>
                    <td> 
                        <button type="button" class="btn btn-danger" id="btnDeleteCall" 
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


// form for NEW user



//role
function getRolesFromAddUserForm() {
    let roles = Array.from(FormRoles.selectedOptions)
        .map(option => option.value);
    let rolesToAdd = [];
    if (roles.includes("2")) {
        let role1 = {
            id: 1,
            name: "ADMIN"
        }
        rolesToAdd.push(role1);
    }
    if (roles.includes("1")) {
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
            getAllUsers();
            usersTableNavLink.click();
            // console.log(getRolesFromAddUserForm() + "----------------------------------------------------")
            // location.reload();

        });
})

//edit
const modalEditExitBtn = document.getElementById("editExit");
const modalEditCloseBtn = document.getElementById("editClose");
const modalEditSubmitBtn = document.getElementById("editSubmit");
const editUsersRoles = document.getElementById("roles");
const editRoleAdminOption = document.getElementById("editRoleAdmin");
const editRoleUserOption = document.getElementById("editRoleUser");

//delete
const deleteRoleAdminOption = document.getElementById("deleteRoleAdmin");
const deleteRoleUserOption = document.getElementById("deleteRoleUser");
const modalDeleteSubmitBtn = document.getElementById("deleteSubmit");
const modalDeleteExitBtn = document.getElementById("deleteExit");
const modalDeleteCloseBtn = document.getElementById("deleteClose");




function getRolesFromEditUserForm() {
    let roles = Array.from(editUsersRoles.selectedOptions)
        .map(option => option.value);
    let rolesToEdit = [];
    if (roles.includes("2")) {
        let role1 = {
            id: 1,
            name: "ADMIN"
        }
        rolesToEdit.push(role1);
    }
    if (roles.includes("1")) {
        let role2 = {
            id: 2,
            name: "USER"
        }
        rolesToEdit.push(role2);
    }
    return rolesToEdit;
}

//tracking submit click Edit/Delete
allUsersTable.addEventListener("click", e => {
    e.preventDefault();
    let delButtonIsPressed = e.target.id === 'btnDeleteCall';
    let editButtonIsPressed = e.target.id === 'btnEditCall';


//getting data for DELETE user

    const deleteUsersId = document.getElementById("dId")
    const deleteUsersName = document.getElementById("dFirstname")
    const deleteUsersLastName = document.getElementById("dLastname")
    const deleteUsersUsername = document.getElementById("dEmail")
    const deleteUsersAge = document.getElementById("dAge")

    if (delButtonIsPressed) {
        let currentUserId = e.target.dataset.id;
        fetch(requestURL + "/" + currentUserId, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json;charset=UTF-8'
            }
        })
            .then(res => res.json())
            .then(user => {
                deleteUsersId.value = user.id;
                deleteUsersName.value = user.firstName;
                deleteUsersLastName.value = user.lastName;
                deleteUsersUsername.value = user.username;
                deleteUsersAge.value = user.age;

                let deleteRoles = user.roles.map(role => role.name)
                deleteRoles.forEach(
                    role => {
                        if (role === "ADMIN") {
                            deleteRoleAdminOption.setAttribute('selected', "selected");

                        } else if (role === "USER") {
                            deleteRoleUserOption.setAttribute('selected', "selected");
                        }
                    })
            })
        $('#modal-delete').modal('show');

        modalDeleteSubmitBtn.addEventListener("click", e => {
            e.preventDefault();
            fetch(`${requestURL}/${currentUserId}`, {
                method: 'DELETE',
            })
                .then(res => res.json());
            modalDeleteExitBtn.click();
            getAllUsers();
            location.reload();
        })
    }

    //getting data for EDIT user

    const editUsersId = document.getElementById("idEdit");
    const editUsersFirstName = document.getElementById("firstNameEdit");
    const editUsersLastName = document.getElementById("lastNameEdit");
    const editUsersAge = document.getElementById("ageEdit");
    const editUsersUsername = document.getElementById("emailEdit");
    const editUsersPassword = document.getElementById("passwordEdit");

    if (editButtonIsPressed) {
        let currentUserId = e.target.dataset.id;
        fetch(requestURL + "/" + currentUserId, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json;charset=UTF-8'
            }
        })
            .then(res => res.json())
            .then(user => {
                editUsersId.value = user.id;
                editUsersFirstName.value = user.firstName;
                editUsersLastName.value = user.lastName;
                editUsersUsername.value = user.username;
                editUsersPassword.value = user.password;
                editUsersAge.value = user.age;

                let editRoles = user.roles.map(role => role.name)
                editRoles.forEach(
                    role => {
                        if (role === "ADMIN") {
                            editRoleAdminOption.setAttribute('selected', "selected");

                        } else if (role === "USER") {
                            editRoleUserOption.setAttribute('selected', "selected");
                        }
                    })
            })
        $('#modal-edit').modal('show');

        modalEditSubmitBtn.addEventListener("click", e => {
            e.preventDefault();
            let user = {
                id: editUsersId.value,
                firstName: editUsersFirstName.value,
                lastName: editUsersLastName.value,
                username: editUsersUsername.value,
                password: editUsersPassword.value,
                age: editUsersAge.value,
                roles: getRolesFromEditUserForm()
            }
            fetch(`${requestURL}`, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                body: JSON.stringify(user)
            })
                // .then(res => console.log(res));
                .then(() => getAllUsers());
            modalEditExitBtn.click();
            console.log("----------------getAllUsers")
            getAllUsers();
            location.reload();
            // modalEditSubmitBtn.reset();
        })
    }
})

//close modal window edit
let removeSelectedRolesFromEditDoc = () => {
    if (editRoleAdminOption.hasAttribute('selected')) {
        editRoleAdminOption.removeAttribute('selected')
    }
    if (editRoleUserOption.hasAttribute('selected')) {
        editRoleUserOption.removeAttribute('selected')
    }
}
modalEditExitBtn.addEventListener("click", e => {
    e.preventDefault();
    removeSelectedRolesFromEditDoc();
})
modalEditCloseBtn.addEventListener("click", e => {
    e.preventDefault();
    removeSelectedRolesFromEditDoc();
})

//close modal window delete
let removeSelectedRolesFromDeleteDoc = () => {
    if (deleteRoleAdminOption.hasAttribute('selected')) {
        deleteRoleAdminOption.removeAttribute('selected')
    }
    if (deleteRoleUserOption.hasAttribute('selected')) {
        deleteRoleUserOption.removeAttribute('selected')
    }
}
modalDeleteExitBtn.addEventListener("click", e => {
    e.preventDefault();
    removeSelectedRolesFromDeleteDoc();
})
modalDeleteCloseBtn.addEventListener("click", e => {
    e.preventDefault();
    removeSelectedRolesFromDeleteDoc();
})