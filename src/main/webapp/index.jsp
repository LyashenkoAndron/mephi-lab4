<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Лица - CRUD операции</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        function loadPersons() {
            $.ajax({
                url: 'person',          // URL для обращения к сервлету
                type: 'GET',
                dataType: 'json',
                success: function(persons) {
                    let tableContent = "<tr><th>ID</th><th>ФИО</th><th>Дата рождения</th></tr>";
                    persons.forEach(function(person) {
                        tableContent += "<tr><td>" + person.id + "</td><td>" + person.fullName + "</td><td>" + person.birthDate + "</td></tr>";
                    });
                    $("#personsTable").html(tableContent);
                },
                error: function() {
                    alert("Ошибка при загрузке данных!");
                }
            });
        }
    </script>
</head>
<body>
    <h2>CRUD операции для таблицы лиц</h2>

    <!-- Форма для добавления нового лица -->
    <form action="person" method="post">
        <input type="hidden" name="action" value="create">
        <label for="fullName">ФИО:</label>
        <input type="text" id="fullName" name="fullName" required><br>

        <label for="birthDate">Дата рождения:</label>
        <input type="date" id="birthDate" name="birthDate" required><br>

        <input type="submit" value="Добавить">
    </form>

    <!-- Форма для обновления лица -->
    <form action="person" method="post">
        <input type="hidden" name="action" value="update">
        <label for="id">ID:</label>
        <input type="number" id="id" name="id" required><br>

        <label for="fullName">Новое ФИО:</label>
        <input type="text" id="fullName" name="fullName"><br>

        <label for="birthDate">Новая дата рождения:</label>
        <input type="date" id="birthDate" name="birthDate"><br>

        <input type="submit" value="Обновить">
    </form>

    <!-- Форма для удаления лица -->
    <form action="person" method="post">
        <input type="hidden" name="action" value="delete">
        <label for="id">ID:</label>
        <input type="number" id="id" name="id" required><br>

        <input type="submit" value="Удалить">
    </form>

</body>
</html>
