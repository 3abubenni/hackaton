export function convertDateFormat(input : string) {
    const parts = input.split('/');
    if (parts.length === 3) {
        const day = parts[0];
        const month = parts[1];
        const year = parts[2];
        const dateInNewFormat = `${year}-${month}-${day}`;
        return dateInNewFormat;
    } else {
        return "Invalid input format";
    }
}