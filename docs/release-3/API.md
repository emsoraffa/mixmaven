# API Documentation

The MixMaven API lets applications easily retrieve and update data on a server.
Both the the java application and the web app utilises the API. This makes it possible to use the java application and web app on to store and interact with the same data.

## Endpoints

The API have the following endpoints:

### Retrieve all drinks `GET` `/drinks`

Retrieves a list of all drinks stored in MixMaven.

### Returns

List of all drinks.

#### Example list of drinks with 1 drink object

```json
[
    {
      "id": "1047dc0f-eca8-4a79-b1ec-5d0fe37810d8",
      "name": "Drink Name",
      "ingredients": [
        {
          "name": "Ingredient Name",
          "alcoholPercentage": 40,
          "amount": 5.0,
          "unit": "ml",
          "type": "alcohol"
        },
        ...
      ],
      "alcoholContent": 0.40,
    },
    ...
]
```

### Update/Edit Drink - `PUT` `/drinks/{id}`

Updates the drink with the given id.

#### Example request `Body`:

```json
{
    "id": "1047dc0f-eca8-4a79-b1ec-5d0fe37810d8",
    "name": "Drink Name",
    "ingredients": [
    {
        "name": "Ingredient Name",
        "alcoholPercentage": 40,
        "amount": 5.0,
        "unit": "ml",
        "type": "alcohol"
    },
    ...

    ],
    "alcoholContent": 0.40
}
```

### Delete Drink - `DELETE` `/drinks/{id}`

Deletes the drink with the corresponding ID.

### Add Drink - `POST` `/drinks`

Adds a new drink to the MixMavenModel.

#### Example request `Body`:

```json
{
    "id": "1047dc0f-eca8-4a79-b1ec-5d0fe37810d8",
    "name": "Drink Name",
    "ingredients": [
    {
        "name": "Ingredient Name",
        "alcoholPercentage": 40,
        "amount": 5.0,
        "unit": "ml",
        "type": "alcohol"
    },
    ...

    ],
    "alcoholContent": 0.40
}
```

### Set File Path - `POST` `/drinks/filepath`

Sets the datafile used for persistence.

#### Example request `Body`:

```json
{
    "filename":"Data.json"
}

```

