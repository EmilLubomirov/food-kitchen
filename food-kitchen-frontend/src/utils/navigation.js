const getNavigation = () => {

    const links = [
        {
            title: 'Home',
            path: '/'
        },
        {
            title: 'Recipes',
            path: '/recipe'
        },
        {
            title: 'Books',
            path: '/book'
        },
        {
          title: 'Add Recipe',
          path: '/recipe/add'
        },
        {
            title: 'Login',
            path: '/login'
        },
        {
            title: 'Register',
            path: '/register'
        }
    ];

    return links;
};

export default getNavigation;