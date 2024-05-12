import React, { useState, useEffect } from 'react';
import "../styles/TextAnimation.css"

interface TextAnimationProps {
    text: string;
    setInputValue: (value : string) => void;
    showButtons: (value : boolean) => void;
}

export const TextAnimation: React.FC<TextAnimationProps> = ({ text, setInputValue ,showButtons }) => {
    const [displayedText, setDisplayedText] = useState('');
    const [showInput, setShowInput] = useState(false);
    
    useEffect(() => {
        let index = 0;
        const interval = setInterval(() => {
        if (index <= text.length) {
            index ? setShowInput(false) : ""
            setDisplayedText(text.slice(0, index));
            index++;
        } else {
            clearInterval(interval);
            setShowInput(true);
            showButtons(true);
        }
        }, 20);
        return () => clearInterval(interval);
    }, [text]);

    const handleChangeValue = (event :  React.ChangeEvent<HTMLInputElement>) =>{
        setInputValue(event.target.value)
    }

    return (
            <div id='texts'>
                {displayedText}
                {showInput ? <p>{">"}<input onChange={handleChangeValue}/></p> : ""}
            </div>
        );
}