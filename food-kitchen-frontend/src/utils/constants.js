const MESSAGES = {

    successfulLogin: "Successfully logged in",
    emptyUsernameOrPassword: "Username and password must not be empty",
    passwordAndConfPasswordMismatch: "Password and confirm password do not match",
    unsuccessfulLogin: "No such user found",
    logoutSuccess: "Logout success",
    addedToFav: "Added to favorites",
    profileChangesSuccess: "Changes saved successfully",
    emptyFields: "Some fields are empty",
    invalidFieldData: "Incorrect field data",
    addedBook: "Book added successfully",
    addedArticle: "Article added successfully",
    addedRecipe: "Recipe added successfully"
};

const MESSAGE_TYPES = {
    success: "success",
    error: "error"
};

module.exports = {
    MESSAGES,
    MESSAGE_TYPES
};