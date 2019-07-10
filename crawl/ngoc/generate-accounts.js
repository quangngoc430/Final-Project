const mysql = require('mysql');
const faker = require('faker');

const numberOfAccounts = 100;
const defaultPassword = '$2a$10$ZW5X4KC5GKvmUYcvgVF2yezD9GCvIZUXjypOOIHU4EO5hcLPYMrpO';//'password';

const connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "root",
  database: "TechShop"
});

let countAccountIsCreated = 0;

let accountIds = [];

connection.connect(function(err) {
  if (err) throw err;
  // console.log("Connected!");

  for (let index = 0; index < numberOfAccounts; index++) {
    const firstName = faker.name.firstName();
    const lastName = faker.name.lastName();

    const name = firstName + ' ' + lastName;
    const email = firstName + lastName + '@gmail.com';
  
    const sql = `INSERT INTO Account(Email, Password, Fullname, RoleID, Status) VALUES ("${email}", "${defaultPassword}", "${name}", "${2}", "${1}");`;
    console.log(sql);
    // connection.query(sql, function (err, result) {
    //   if (err) throw err;
    //   countAccountIsCreated = countAccountIsCreated + 1;
    //   accountIds.push(result.insertId);

    //   if (countAccountIsCreated == numberOfAccounts) {
    //     connection.end();
    //   }
    // });
  }
});