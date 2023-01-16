# README.md
## 課題

> KotlinとSpring Bootを利用し、書籍管理システムの構築をお願いします。
> - 書籍には著者の属性があり、書籍と著者の情報をRDBに登録・変更・検索ができる
> - 著者に紐づく本を取得できる
> - APIのみ作成いただく形でお願いします。
>   可能な範囲でテストコードも作成をお願いします。

## 実行方法

### ローカル実行(MacOS)

※以下のコマンドから起動する場合は、Mavenのインストールをお願いします。

```sh
mvn spring-boot:run
```

### テスト実行

```sh
mvn test
```

## 主な使用技術・ライブラリ

| 使用技術 | 補足 |
| --- | --- |
| Java 11 |  |
| Kotlin 1.6.21 |  |
| SpringBoot 2.7.8 |  |
| Spring Data JPA | SpringBootのO/Rマッパー |
| H2 Database | インメモリデータベースとして利用 |
| log4j2 | ロギングライブラリ |
| JUnit4 | テストフレームワーク |
| Mockito-all | テストモック用ライブラリ |

## 仕様

### API定義

以下のSwagger参照。  
`doc/swagger.yaml`

### DB定義

Springアプリケーション起動時に、DBスキーマ定義 `resources/schema.sql` および、サンプルデータ `resources/data.sql` が読み込まれます。

#### テーブル定義

**bookテーブル**

| 物理名 | 論理名 | データ型 | Primary Key | NOT NULL | 補足 |
| --- | --- | --- | --- | --- | --- |
| id | id | INTEGER | ◯ |  | 自動採番 |
| title | 書籍名 | VARCHAR(255) |  | ◯ |  |
| author | 著者名 | VARCHAR(255) |  | ◯ |  |

