# ![possys thumbnail][possys-thumbnail] POSsys ![possys badge][possys-badge]
A Console application that can process sale transactions of a store. This application features a computer store. It has a database where products are retrieved and transactions are saved. Users can navigate through the console using the numeric menu. When starting a new transaction, user can add items, check out and generate their receipt. Other transactions can also be done through the application like checking the inventory, retrieving previous transactions and reviewing reports.

## Table of Contents
- [Features](#features)
- [Entity Relationship Model](#entity-relationship-model)
- [Installation](#installation)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Features
### Main Features
![new transaction][new-transaction] &nbsp;
![payment][payment] &nbsp;
![receipt][receipt]
- **New Transaction** - Add or search for an item and a receipt will be generated containing the necessary transaction information.
- **Item Searching** - Items can be searched using item ID, keywords or classification. By inputting an item's ID or typing the keyword, the application automatically knows if it's an ID or a keyword.
- **Receipt** - Processing a transaction will generate a receipt containing the items, prices, payment, VAT, transaction ID and date-time.

![inventory][inventory] &nbsp;
![transaction list][transaction-list] &nbsp;
![transaction][transaction]
- **Inventory** - Check the stock and price of any product.
- **Transaction List** - Check the list of all transactions and retrieve transaction information.
- **Statistics** - Display the total items sold, transactions, gross and net amount.

### Additional Features
![main menu][main-menu] &nbsp;
![add item][add-item]
- **Numeric Menu** - Navigate the application using numbers assigned to every action/selection.
- **Colored Text** - Helps user identify choices and navigate the application.

## Entity Relationship Model
![Entity Relationship Model][erm]

## Installation
1. Download the latest version of [POSsys][release-page].
2. Install [POSsys-1.0.0-Beta.exe][latest-release].

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments
- **[Visual Studio Code][visual-studio-code]**: For development environment.
- **[Advanced Installer][advanced-installer]**: For installer.
- **[Database Tour][database-tour]**: For database manager.
- **[SQLite][sqlite]**: For serverless database.

<!-- Reference -->
[possys-thumbnail]: https://github.com/AHG-BSCS/Console-Point-of-Sales/assets/130748576/03c2b6a3-5b94-4f99-9a51-8dbc737f435e
[possys-badge]: https://img.shields.io/badge/Console-Point_of_Sale_System-245F75

[new-transaction]: https://github.com/AHG-BSCS/Console-Point-of-Sales/assets/130748576/b267ddc4-ff80-4319-8227-b476fb5e3c2b
[payment]: https://github.com/AHG-BSCS/Console-Point-of-Sales/assets/130748576/9be7fb61-8acc-471b-8798-1717a67e1376
[receipt]: https://github.com/AHG-BSCS/Console-Point-of-Sales/assets/130748576/5903c895-7b63-454c-9509-42a446a82a1e
[inventory]: https://github.com/AHG-BSCS/Console-Point-of-Sales/assets/130748576/610c1136-bab4-46d3-b054-efb535bb22a5
[transaction-list]: https://github.com/AHG-BSCS/Console-Point-of-Sales/assets/130748576/0bd05196-b5e6-4479-a900-63efcfe32817
[transaction]: https://github.com/AHG-BSCS/Console-Point-of-Sales/assets/130748576/309bc50d-a280-4c40-ab43-78ad9300ee27
[main-menu]: https://github.com/AHG-BSCS/Console-Point-of-Sales/assets/130748576/c820739b-46d8-4135-abaa-2e0d92584d8c
[add-item]: https://github.com/AHG-BSCS/Console-Point-of-Sales/assets/130748576/2ac1ff18-f98c-4577-9040-8230740c178a
[erm]: https://github.com/AHG-BSCS/Console-Point-of-Sales/assets/130748576/82ea20a2-6377-4577-bf9d-66db784748c8

[release-page]: https://github.com/Mindkerchief/README-MKC-Standard/releases
[latest-release]: https://github.com/AHG-BSCS/Console-Point-of-Sales/releases/download/v1.0.0-Beta/POSsys-1.0.0-Beta.exe
[visual-studio-code]: https://code.visualstudio.com/docs
[advanced-installer]: https://www.advancedinstaller.com/user-guide/using.html
[database-tour]: https://www.databasetour.net/documentation/contents.htm
[sqlite]: https://www.sqlite.org/docs.html
