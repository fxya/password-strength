const app = Vue.createApp({
    data() {
        return {
            password: '',
            passwordStrength: ''
        };
    },
    mounted() {
        this.setupWebSocket();
    },
    methods: {
        setupWebSocket() {
            // Connect to the WebSocket endpoint
            const socket = new WebSocket('ws://localhost:8080/api/password/strength');

            socket.onopen = () => {
                console.log('WebSocket connection established.');
            };

            socket.onmessage = (event) => {
                this.passwordStrength = event.data;
            };

            socket.onclose = () => {
                console.log('WebSocket connection closed.');
            };

            socket.onerror = (error) => {
                console.error('WebSocket error:', error);
            };

            this.socket = socket; // Save the socket instance to access it later
        },
        checkPasswordStrength() {
            // Send the password to the WebSocket server for evaluation
            if (this.socket.readyState === WebSocket.OPEN) {
                this.socket.send(this.password);
            }
        }
    }
});

app.mount('#contents');
