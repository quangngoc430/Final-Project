const csv=require('csvtojson')
const mysql = require('mysql');
const random = require('random');
const utf8 = require('utf8');

const con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "root",
  database: "TechShop"
});

async function run() {
  let idsAndCategoryIDs = [];

  let jsonArray = await csv().fromFile('urls.csv');

  for (element of jsonArray) {
    idsAndCategoryIDs.push([element.ID, element.categoryID]);
  }
  console.log(idsAndCategoryIDs);

  items = await csv().fromFile('temp.csv');

  con.connect(function(err) {
    if (err) throw err;
    console.log("Connected!");
  
    let index = 0

    for (item of items) {
      let re = new RegExp('\"', 'g');
      title = item.title.replace(re, '///\\');

      if (title !== "None") {

        price = (item.price === '' | item.price === 'Liên h') ? 500000 : item.price;
        image_item_origin = item.image_item_origin.replace(re, '///\\');
        item_infos = item.item_infos.replace(re, '///\\');
        item_infos_technical = item.item_infos_technical.replace(re, '///\\');

        var sql = `INSERT INTO Item(Name, Price, Amount, Warranty, ImageURLs, Infos, Technical_Infos, CategoryID) VALUES ("${title}", "${price}", "${random.int(min = 1, max = 100)}", "${item.warranty}", "${image_item_origin}", "${item_infos}", "${item_infos_technical}", ${Number(idsAndCategoryIDs[index][1]) + 1});`;

        //console.log(sql)

        con.query(sql, function (err, result) {
          if (err) throw err;
          console.log("1 record inserted");
        });
        index = index + 1;
        console.log(index);
      }
    }
  });
}

run();

