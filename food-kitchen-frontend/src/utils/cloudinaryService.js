import { Cloudinary as CoreCloudinary, Util } from 'cloudinary-core';

const url = (publicId, options) => {
    const scOptions = Util.withSnakeCaseKeys(options);
    const cl = CoreCloudinary.new();
    return cl.url(publicId, scOptions);
};

const openUploadWidget = (options, callback) => {
    const scOptions = Util.withSnakeCaseKeys(options);
    window.cloudinary.openUploadWidget(scOptions, callback);
};

export const beginUpload = (setImageUrl) => {

    const uploadOptions = {
        cloudName: process.env.REACT_APP_CLOUD_NAME,
        // tags: [tag],
        uploadPreset: process.env.REACT_APP_UPLOAD_PRESET
    };

    openUploadWidget(uploadOptions, (error, photos) => {
        if (!error) {

            if(photos.event === "success"){
                setImageUrl(photos.info.secure_url);
            }
        } else {
            console.log(error);
        }
    });
};