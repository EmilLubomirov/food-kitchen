const getNavigation = () => {

    const links = [
        {
            title: 'Home',
            path: '/'
        },
        {
            title: 'Recipes',
            path: '/recipes'
        },
        {
            title: 'Books',
            path: '/books'
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