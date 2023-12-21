const express = require('express');
const bodyParser = require('body-parser');
const mysql = require('mysql');

const app = express();
const port = 3000;

app.use(bodyParser.json());

const db = mysql.createConnection({
    host: '127.0.0.1',
    port: 3306,
    user: 'zakaria',
    password: 'root1234',
    database: 'DepenseTelecom',
});

db.connect(err => {
    if (err) {
        console.error('MySQL connection error:', err.stack);
        return;
    }
    console.log('Connected to MySQL database');
});

app.post('/performQuery', (req, res) => {
    const sql = req.body.sql;

    db.query(sql, (err, result) => {
        if (err) {
            console.error('MySQL query error:', err.stack);
            res.status(500).send('Internal Server Error');
            return;
        }

        res.json(result);
    });
});

app.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
