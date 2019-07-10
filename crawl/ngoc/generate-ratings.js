const mysql = require('mysql');
const fs = require('fs');

const comments = ['sản phẩm tốt giá ưu đãi',
'office là hàng chính hãng, mà sao gía cao hơn nhiều shop vậy ad',
'sản phẩm giá tốt, chạy mượt',
'sản phẩm k như mong đơi',
'sản phẩm đúng như mô tả',
'good',
'perfect item',
'giá rẻ phù hợp với chất lượng',
'case tốt',
'phù hơp vs sinh viên',
'sản phẩm có vấn đề ở ngoài case',
'ổn định',
'không hài lòng',
'hàng rất tốt, cho shop 5 sao',
'giá ok, nhân viên nhiệt tình',
'thay mặt công ty chân thành cảm ơn shop',
'sản phẩm rất ok',
'có mua trả góp được khôg shop',
'mẫu tốt hơn của dòng này có không shop',
'trên cả tuyệt vời',
'sản phẩm phù hợp mức gía, tư vấn nhiệt tình',
'máy đẹp',
'hiệu năng đúng như mong muốn',
'tốt',
'đúng như mô tả',
'máy chạy êm',
'tốt, các bạn nên mua',
'mẫu mã đẹp',
'mặc hàng này còn không',
'bảo hành bao lâu v shop',
'có thể nâng cấp được không',
'shop ơi',
'máy giống trông ảnh không',
'máy dùng được, nhưng có vẻ kp là máy mới',
'hàng , giá mềmm, nên mua',
'cho xin sđt shop với',
'dòng này shop khác bán rẻ sao ở đây giá cao vậy',
'không như mô tả',
'bào hành ở đâu shop',
'phí ship như thế nào',
'nhận hàng và hài lòng',
'hàng mới hay cũ v shop',
'sản phẩm đạt 8/10 mong đợi',
'thất vọng',
'hàng tốt giá rẻ',
'shop uy tín',
'giá chấp nhận được',
'ưu đãi tốt',
'no cmt',
'cho shop 5 sao'];
const numberOfAccounts = 100;
const numberOfITems = 2801;

const connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "root",
  database: "TechShop"
});

connection.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");

  for (let accountIndex = 1; accountIndex <= numberOfAccounts; accountIndex++) {
    const itemIds = [];
    for (let index = 0; index < 20; index++) {
      
      let itemIndex = null;
      while(true) {
        itemIndex = randomInt(1, numberOfITems);

        if (itemIds.includes(itemIndex) === false) {
          break;
        }
      }

      const sql = `INSERT INTO Rating(Value, Comment, ItemID, AccountID) VALUES ("${randomInt(1, 5)}", "${comments[randomInt(0, 49)]}", "${itemIndex}", "${accountIndex}");`;

      fs.appendFileSync(__dirname + '/ratings.txt', sql + '\n', 'utf8');

      connection.query(sql, function (err, result) {
        if (err) throw err;
        
        console.log(result);
      });
    }
  }
});

function randomInt(low, high) {
  return Math.floor(Math.random() * (high - low) + low)
}