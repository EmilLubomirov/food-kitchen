const getNavigation = (user) => {

    const commonLinks = [
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
    ];

    const guestLinks = [
        ...commonLinks,
        {
            title: 'Login',
            path: '/login'
        },
        {
            title: 'Register',
            path: '/register'
        }
    ];

    const userLinks = [
        ...commonLinks,
        {
            title: 'Add Recipe',
            path: '/recipe/add'
        },
        {
            title: 'Forum',
            path: '/forum'
        }
    ];

    if (!user){
        return guestLinks;
    }

    return userLinks;
};

export default getNavigation;