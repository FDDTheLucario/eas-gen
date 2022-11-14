import {Terminal} from 'terminal-kit';

const termkit = require('terminal-kit');
const term: Terminal = termkit.terminal;

term.fullscreen(true);
term('This is a test');